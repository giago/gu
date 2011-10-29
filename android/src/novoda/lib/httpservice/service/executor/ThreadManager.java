package novoda.lib.httpservice.service.executor;

import static novoda.lib.httpservice.util.Log.i;
import static novoda.lib.httpservice.util.Log.v;
import static novoda.lib.httpservice.util.Log.verboseLoggingEnabled;
import static novoda.lib.httpservice.util.Log.w;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import novoda.lib.httpservice.exception.ExecutorException;
import novoda.lib.httpservice.provider.EventBus;
import novoda.lib.httpservice.provider.IntentRegistry;
import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Response;
import novoda.lib.httpservice.service.monitor.Monitorable;
import android.content.Intent;

public class ThreadManager implements ExecutorManager {

	private ThreadPoolExecutor poolExecutor;

	private ExecutorCompletionService<Response> completitionService;

	private Thread looperThread;

	private CallableExecutor<Response> callableExecutor;

	private EventBus eventBus;

	private boolean runLoop = true;
	
	private IntentRegistry requestRegistry;
	
	public ThreadManager(IntentRegistry requestRegistry, ThreadPoolExecutor poolExecutor, EventBus eventBus, CallableExecutor<Response> callableExecutor) {
		this(requestRegistry, poolExecutor, eventBus, callableExecutor, null);
	}

	public ThreadManager(IntentRegistry requestRegistry, ThreadPoolExecutor poolExecutor, EventBus eventBus, CallableExecutor<Response> callableExecutor,
			ExecutorCompletionService<Response> completitionService) {
		if (verboseLoggingEnabled()) {
			v("Starting thread manager");
		}
		if(requestRegistry == null) {
			throw new ExecutorException("The request registry is null!");
		}
		this.requestRegistry = requestRegistry;
		
		this.completitionService = completitionService;
		if (this.completitionService == null) {
			this.completitionService = (ExecutorCompletionService<Response>) getCompletionService(poolExecutor);
		}
		
		this.poolExecutor = poolExecutor;
		this.eventBus = eventBus;
		this.callableExecutor = callableExecutor;
	}

	@Override
	public void shutdown() {
		if(poolExecutor != null) {
			if (verboseLoggingEnabled()) {
				v("Shutting down pool executor");
			}
			poolExecutor.shutdown();
			while(poolExecutor.isTerminating()) {
				if (verboseLoggingEnabled()) {
					v("Thread Manager : waiting for shut down of poolExecutor...");
				}
			}
			if (verboseLoggingEnabled()) {
				v("Thread Manager : poolExecutor is terminated...");
			}
		}
		if(looperThread != null) {
			if (verboseLoggingEnabled()) {
				v("Thread Manager : Shutting down looperThread");
			}
			runLoop = false;
			if (verboseLoggingEnabled()) {
				v("Thread Manager : looperThread is terminated");
			}
		}
	}

	@Override
	public void addTask(Intent intent) {
		IntentWrapper intentWrapper = new IntentWrapper(intent);
		if(requestRegistry.isInQueue(intentWrapper)) {
			i("Thread Manager : Skipping intent a similar in being processed");
			return;
		}
		if(requestRegistry.isInCache(intentWrapper)) {
			i("Thread Manager : Skipping intent a similar intent has being processed a few seconds ago");
			eventBus.fireOnContentConsumed(intentWrapper);
			return;
		}
		Callable<Response> callable = callableExecutor.getCallable(intentWrapper);
		if (callable == null) {
			throw new ExecutorException(
					"The callable retrieve from the service to hanble the intent is null");
		}
		completitionService.submit(callable);
	}

	@Override
	public boolean isWorking() {
		if(poolExecutor.getActiveCount() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void start() {
		if (verboseLoggingEnabled()) {
			v("Thread Manager : Starting Thread Loop");
		}
		looperThread = new Thread() {
			public void run() {
				Thread.currentThread().setPriority(NORM_PRIORITY-1);
				if (verboseLoggingEnabled()) {
					v("Thread Manager : is running now");
				}
				while (runLoop) {
					try {
						if (verboseLoggingEnabled()) {
							v("Thread Manager : new cycle");
						}
						Future<Response> future = completitionService.take();
						Response response = future.get();
						future.cancel(true);
						if (verboseLoggingEnabled()) {
							v("Response received");
						}
						if(response != null && response.getIntentWrapper() != null) {
							if (verboseLoggingEnabled()) {
								v("Firing content received");
							}
							eventBus.fireOnContentReceived(response);
							eventBus.fireOnContentConsumed(response.getIntentWrapper());
						} else {
							if (verboseLoggingEnabled()) {
								v("Response or intentWrapper are null, there must be something wrong");
							}
						}
					} catch (InterruptedException e) {
						w("InterruptedException", e);
					} catch (ExecutionException e) {
						w("ExecutionException", e);
					}
				}
				if (verboseLoggingEnabled()) {
					v("Thread Manager : ending cycle");
				}
			};
		};
		looperThread.start();
	}

	private final CompletionService<Response> getCompletionService(ThreadPoolExecutor poolExecutor) {
		return new ExecutorCompletionService<Response>(poolExecutor);
	}

	@Override
	public Map<String, String> dump() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Monitorable.POOL_SIZE, Monitorable.EMPTY + poolExecutor.getPoolSize());
		map.put(Monitorable.ACTIVE_COUNT, Monitorable.EMPTY + poolExecutor.getActiveCount());
		map.put(Monitorable.TASK_COUNT, Monitorable.EMPTY + poolExecutor.getTaskCount());
		map.put(Monitorable.COMPLETED_TASKS, Monitorable.EMPTY + poolExecutor.getCompletedTaskCount());
		map.put(Monitorable.IS_SHUTDOWN, Monitorable.EMPTY + poolExecutor.isShutdown());
		map.put(Monitorable.IS_TERMINATED, Monitorable.EMPTY + poolExecutor.isTerminated());
		map.put(Monitorable.IS_TERMINATING, Monitorable.EMPTY + poolExecutor.isTerminating());
		return map;
	}

}