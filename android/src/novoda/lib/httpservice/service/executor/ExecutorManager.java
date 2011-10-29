package novoda.lib.httpservice.service.executor;

import novoda.lib.httpservice.service.monitor.Dumpable;
import android.content.Intent;

public interface ExecutorManager extends Dumpable{

	void shutdown();

	void addTask(Intent intent);
	
	boolean isWorking();
	
	void start();
	
	void pause();

}
