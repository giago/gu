package novoda.lib.httpservice.processor;


/**
 * Simple interface to mark object that are able to add and remove from a collection of 
 * some kind of Processor.
 * 
 * @author luigi@novoda.com
 */
public interface HasProcessors {
	
	void remove(Processor processor);

	void add(Processor processor);
	
}
