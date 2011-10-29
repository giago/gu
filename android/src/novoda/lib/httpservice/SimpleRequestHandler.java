package novoda.lib.httpservice;

import novoda.lib.httpservice.handler.RequestHandler;
import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Response;

/**
 * Empty implementation of RequestHandler always matching.
 * Nice to extends so that you can override only the necessary methods
 * 
 * @author luigi@novoda.com
 *
 */
public class SimpleRequestHandler implements RequestHandler {

	@Override
	public void onContentReceived(IntentWrapper intentWrapper, Response response) {
	}

	@Override
	public void onHeadersReceived(IntentWrapper intentWrapper, String headers) {
	}

	@Override
	public void onStatusReceived(IntentWrapper intentWrapper, String status) {
	}

	@Override
	public void onThrowable(IntentWrapper intentWrapper, Throwable t) {
	}

	@Override
	public boolean match(IntentWrapper intentWrapper) {
		return true;
	}

	@Override
	public void onContentConsumed(IntentWrapper intentWrapper) {
	}

}
