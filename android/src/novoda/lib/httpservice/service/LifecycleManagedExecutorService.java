package novoda.lib.httpservice.service;

import static novoda.lib.httpservice.util.Log.v;
import static novoda.lib.httpservice.util.Log.verboseLoggingEnabled;
import static novoda.lib.httpservice.util.Log.w;

import java.util.Timer;
import java.util.TimerTask;

import novoda.lib.httpservice.provider.EventBus;
import novoda.lib.httpservice.provider.IntentRegistry;
import novoda.lib.httpservice.service.executor.ExecutorManager;
import android.content.Intent;

public abstract class LifecycleManagedExecutorService extends ExecutorService {
	
	private static final long SERVICE_LIFECYCLE = 1000 * 30;
	
	private static final long KEEP_ALIFE_TIME = 1000 * 60 * 3;
	
	private long lastCall;
	
	private Timer timer;
	
	public LifecycleManagedExecutorService(IntentRegistry requestRegistry, EventBus eventBus, ExecutorManager executorManager) {
		super(requestRegistry, eventBus, executorManager);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		lastCall = System.currentTimeMillis();		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onCreate() {
		if (verboseLoggingEnabled()) {
			v("Lifecycle manager: Starting lifecycle");
		}
		startLifeCycle();
		super.onCreate();
	}
	
	public void startLifeCycle() {	
		try {
			lastCall = System.currentTimeMillis();
			timer = new Timer();
			TimerTask monitorThread = new TimerTask() {
				@Override
				public void run() {
					boolean working = isWorking();
					long delta = System.currentTimeMillis() - lastCall;
					if (verboseLoggingEnabled()) {
						v("Lifecycle manager: working? " + working + " last execution was? " + delta);
					}
					if (working || delta < KEEP_ALIFE_TIME) {
						if (verboseLoggingEnabled()) {
							v("Lifecycle manager: keeping alive the service");
						}      
					} else {
						if (verboseLoggingEnabled()) {
							v("Lifecycle manager: stoping service");
						}		
						stopLifeCycle();
						stopSelf();
					}
				}
			};
			timer.schedule(monitorThread, 0, SERVICE_LIFECYCLE);
		} catch(Throwable t) {
			w("Lifecycle manager: Scheduling timer already scheduled", t);
		}
	}

	public void stopLifeCycle() {
		try {
			if (verboseLoggingEnabled()) {
				v("Lifecycle manager: removing timer");
			}
			timer.cancel();
			timer.purge();
		} catch(Throwable t) {
			w("Lifecycle manager: Cancel on a not scheduled timer", t);
		}
	}
	
}
