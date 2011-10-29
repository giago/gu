package novoda.lib.httpservice.service.monitor;

import java.util.Map;

public interface Monitor {

	void update(Map<String, String> parameters);

	long getInterval();

}
