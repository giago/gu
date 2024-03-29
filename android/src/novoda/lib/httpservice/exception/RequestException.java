package novoda.lib.httpservice.exception;

import static novoda.lib.httpservice.util.Log.e;

public class RequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestException(String msg) {
		super(msg);
	}
	
	public RequestException(String msg, Throwable t) {
		super(t.getMessage());
		e("Exception consuming content", t);
	}
	
}
