package novoda.lib.httpservice.handler;

import novoda.lib.httpservice.provider.IntentWrapper;

public interface GlobalHandler extends RequestHandler {
	
	void onProgress(IntentWrapper intentWrapper);

}
