package novoda.lib.httpservice.handler;

import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Response;

public interface RequestHandler {
	
	boolean match(IntentWrapper request);

	void onStatusReceived(IntentWrapper intentWrapper, String status);

    void onHeadersReceived(IntentWrapper intentWrapper, String headers);

    void onThrowable(IntentWrapper intentWrapper, Throwable t);

	void onContentReceived(IntentWrapper intentWrapper, Response response);
	
	void onContentConsumed(IntentWrapper request);
	
}
