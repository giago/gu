package novoda.lib.httpservice.service.monitor;

import static novoda.lib.httpservice.util.Log.v;
import static novoda.lib.httpservice.util.Log.verboseLoggingEnabled;
import static novoda.lib.httpservice.util.Log.w;

import java.util.Timer;
import java.util.TimerTask;

public class MonitorManager implements Monitorable  {
	
	private Monitor monitor;
	
	private Timer timer;
	
	private Dumpable toDump;
	
	public MonitorManager(Dumpable toDump) {
		this.toDump = toDump;
	}
	
	@Override
	public void attach(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void startMonitoring() {	
		if (verboseLoggingEnabled()) {
			v("Starting monitoring the executor manager");
		}
		try {
			timer = new Timer();
			TimerTask monitorThread = new TimerTask() {
				@Override
				public void run() {
					if(toDump == null) {
						stopMonitoring();
					}
					monitor.update(toDump.dump());
				}
			};
			timer.schedule(monitorThread, 0, monitor.getInterval());
		} catch(Throwable t) {
			w("Scheduling timer already scheduled", t);
		}
	}

	@Override
	public void stopMonitoring() {
		try {
			if(timer != null) {
				timer.cancel();
				timer.purge();
			}
		} catch(Throwable t) {
			w("Cancel on a not scheduled timer", t);
		}
	}

}
