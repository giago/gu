package novoda.lib.httpservice.provider;

/**
 * Provider define the interface for every kind of
 * content provider. In 99% of the scenario the content is 
 * some result coming from an Http Request.
 * 
 * @author luigi
 *
 */
public interface Provider {

	/**
	 * Execute a particular http request and return the  
	 * 
	 * @param intentWrapper
	 * @return
	 */
	Response execute(IntentWrapper intentWrapper);
	
}
