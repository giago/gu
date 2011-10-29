package novoda.lib.httpservice.processor;

import novoda.lib.httpservice.provider.IntentWrapper;

import org.apache.http.protocol.HttpProcessor;

/**
 * A processor is the interface to mark objects that are able
 * to process request before and the response after the execution
 * of a http method.
 * 
 * @author luigi@novoda.com
 *
 */
public interface Processor extends HttpProcessor {

	/**
	 * The match method is used from the event bus
	 * to determine if a specific processor is suppose
	 * to intercept request and response.
	 * 
	 * @param request
	 * @return
	 */
	boolean match(IntentWrapper request);
	
}
