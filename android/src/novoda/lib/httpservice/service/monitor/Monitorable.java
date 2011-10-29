package novoda.lib.httpservice.service.monitor;


public interface Monitorable {

	String POOL_SIZE = "PoolSize";

	String ACTIVE_COUNT = "ActiveCount";

	String TASK_COUNT = "TaskCount";

	String COMPLETED_TASKS = "CompletedTask";

	String IS_SHUTDOWN = "isShutdown";

	String IS_TERMINATED = "isTerminated";

	String IS_TERMINATING = "isTerminating";
	
	String EMPTY = "";

	void attach(Monitor monitor);

	void startMonitoring();

	void stopMonitoring();

}
