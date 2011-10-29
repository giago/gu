
package novoda.lib.httpservice.service;

import static novoda.lib.httpservice.util.Log.v;
import static novoda.lib.httpservice.util.Log.verboseLoggingEnabled;

import java.util.Map;

import novoda.lib.httpservice.handler.HasHandlers;
import novoda.lib.httpservice.handler.RequestHandler;
import novoda.lib.httpservice.processor.HasProcessors;
import novoda.lib.httpservice.processor.Processor;
import novoda.lib.httpservice.provider.EventBus;
import novoda.lib.httpservice.provider.IntentRegistry;
import novoda.lib.httpservice.provider.Response;
import novoda.lib.httpservice.service.executor.CallableExecutor;
import novoda.lib.httpservice.service.executor.ConnectedThreadPoolExecutor;
import novoda.lib.httpservice.service.executor.ExecutorManager;
import novoda.lib.httpservice.service.executor.ThreadManager;
import novoda.lib.httpservice.service.monitor.Dumpable;
import novoda.lib.httpservice.service.monitor.Monitor;
import novoda.lib.httpservice.service.monitor.MonitorManager;
import novoda.lib.httpservice.service.monitor.Monitorable;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Main Executor Service.
 * 
 * @author luigi
 *
 * @param <T>
 */
public abstract class ExecutorService extends Service implements CallableExecutor<Response>, HasHandlers, HasProcessors, Dumpable, Monitorable {
    
    protected ExecutorManager executorManager;
    
    private MonitorManager monitorManager;
    
    protected EventBus eventBus;
    
    protected IntentRegistry requestRegistry;
    
    public ExecutorService(IntentRegistry requestRegistry, EventBus eventBus, ExecutorManager executorManager) {
    	super();
    	this.requestRegistry = requestRegistry;
    	if(this.requestRegistry == null) {
    		this.requestRegistry = new IntentRegistry(); 
    	}
    	this.eventBus = eventBus;
    	if(this.eventBus == null) {
    		this.eventBus = new EventBus(this.requestRegistry); 
    	}
    	this.executorManager = executorManager; 
    	if(this.executorManager == null) {
    		ConnectedThreadPoolExecutor pool = new ConnectedThreadPoolExecutor(this);
    		this.executorManager = new ThreadManager(this.requestRegistry, pool, this.eventBus, this);
    	}
    	this.monitorManager = new MonitorManager(this);
    }

	@Override
    public void onCreate() {
		if(verboseLoggingEnabled()) {
    		v("Executor Service on Create");
    	}
		executorManager.start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
    	if(verboseLoggingEnabled()) {
    		v("Executor Service on Destroy");
    	}
    	stopMonitoring();
    	if(executorManager != null) {
    		executorManager.shutdown();
    	}
        super.onDestroy();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	if(verboseLoggingEnabled()) {
    		v("Executing intent");
    	}
        executorManager.addTask(intent);
        return START_NOT_STICKY;
    }

	@Override
    public IBinder onBind(Intent intent) {
        return null;
    }
	
	public boolean isWorking() {
		return executorManager.isWorking();
	}
	
	//==============================================================
	//Relative to the monitor functionalities
	//==============================================================
    
	@Override
    public Map<String, String> dump() {
    	return executorManager.dump();
    }
	
	@Override
	public void startMonitoring() {
		monitorManager.startMonitoring();
	}
	
	@Override
	public void stopMonitoring() {
		monitorManager.stopMonitoring();
	}
	
	@Override
	public void attach(Monitor monitor) {
		monitorManager.attach(monitor);
	}

	//==============================================================
	//Relative handlers and processors for the eventBus
	//==============================================================
	
	@Override
	public void add(RequestHandler handler) {
		eventBus.add(handler);
	}

	@Override
	public void remove(RequestHandler handler) {
		eventBus.remove(handler);
	}
	
	@Override
	public void add(Processor processor) {
		eventBus.add(processor);
	}

	@Override
	public void remove(Processor processor) {
		eventBus.remove(processor);
	}
	
}
