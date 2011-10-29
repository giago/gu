package novoda.lib.httpservice.service.executor;

import static novoda.lib.httpservice.util.Log.v;
import static novoda.lib.httpservice.util.Log.verboseLoggingEnabled;

import java.util.concurrent.Callable;

import novoda.lib.httpservice.exception.HandlerException;
import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Provider;
import novoda.lib.httpservice.provider.Response;

/**
 * Wrapper for the callable.
 * 
 * @author luigi@novoda.com
 *
 */
public class CallableWrapper implements Callable<Response> {
	
	private Provider provider;
	
	private IntentWrapper request;
	
	public CallableWrapper(Provider provider, IntentWrapper request) {
		if(provider == null) {
			throw new HandlerException("Configuration problem! A Provider must be specified!");
		}
		this.provider = provider;
		if(request == null) {
			throw new HandlerException("Configuration problem! Request must be specified!");
		}
		this.request = request;
	}
	
	@Override
	public Response call() throws Exception {
		if(verboseLoggingEnabled()) {
			v("Executing request : " + request);
		}
		Response response = provider.execute(request);
		if(verboseLoggingEnabled()) {
			v("Response received");
		}
		return response;
	}

}
