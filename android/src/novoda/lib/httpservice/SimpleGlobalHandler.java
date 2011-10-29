package novoda.lib.httpservice;

import novoda.lib.httpservice.handler.GlobalHandler;
import novoda.lib.httpservice.provider.IntentWrapper;

/**
 * Empty implementation of GlobalHandler always matching.
 * Nice to extends so that you can override only the necessary methods
 * 
 * @author luigi@novoda.com
 *
 */
public class SimpleGlobalHandler extends SimpleRequestHandler implements GlobalHandler {

	@Override
	public void onProgress(IntentWrapper intentWrapper) {
	}

}
