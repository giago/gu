package novoda.lib.httpservice.provider.http;

import static novoda.lib.httpservice.util.Log.Provider.e;
import static novoda.lib.httpservice.util.Log.Provider.errorLoggingEnabled;
import static novoda.lib.httpservice.util.Log.Provider.v;
import static novoda.lib.httpservice.util.Log.Provider.verboseLoggingEnabled;
import novoda.lib.httpservice.exception.ProviderException;
import novoda.lib.httpservice.provider.EventBus;
import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Provider;
import novoda.lib.httpservice.provider.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpProvider implements Provider {
	
	private static final String USER_AGENT = new UserAgent.Builder().with("HttpService").build();
    
    private HttpClient client;
    
    private EventBus eventBus;

    public HttpProvider(EventBus eventBus) {		
		this(AndroidHttpClient.newInstance(USER_AGENT), eventBus);
	}
    
    public HttpProvider(HttpClient client, EventBus eventBus) {		
    	if(eventBus == null) {
    		logAndThrow("EventBus is null, can't procede");
    	}
		this.eventBus = eventBus;
		this.client = client;
	}

	@Override
	public Response execute(IntentWrapper request) {
		Response response = new Response();
        HttpUriRequest method = null;
        try {
        	if(verboseLoggingEnabled()) {
    			v("HttpProvider execute for : " + request.getUri());
    		}
        	if(request.isGet()) {
        		method = new HttpGet(request.asURI());
        	} else if(request.isPost()) {
        		method = new HttpPost(request.asURI());
//        		checkMultipartParams((HttpPost)method, request);
        	} else {
        		logAndThrow("Method " + request.getMethod() + " is not implemented yet");
        	}
        	HttpContext context = new BasicHttpContext();
        	eventBus.fireOnPreProcess(request, method, context);
        	final HttpResponse httpResponse = client.execute(method, context);
        	eventBus.fireOnPostProcess(request, httpResponse, context);
            if(httpResponse == null) {
            	logAndThrow("Response from " + request.getUri() + " is null");
            }
            response.setHttpResponse(httpResponse);
            response.setIntentWrapper(request);
            if(verboseLoggingEnabled()) {
    			v("Request returning response");
    		}
            return response;
        } catch (Throwable t) {
        	eventBus.fireOnThrowable(request, t);
        	if(errorLoggingEnabled()) {
    			e("Problems executing the request for : " + request.getUri(), t);
    		}
    		return null;
        }
	}
	
//	private void checkMultipartParams(HttpPost post, IntentWrapper intent) {
//		String fileParamName = intent.getMultipartFileParamName();
//		FileBody fileBody = getFileBodyFromFile(intent.getMultipartFile(), fileParamName);
//
//		String uriParamName = intent.getMultipartUriParamName();
//		FileBody uriBody = getFileBodyFromUri(intent.getMultipartUri(), uriParamName);
//		
//		String extraPram = intent.getMultipartExtraParam();
//		StringBody stringBody = getStringBody(extraPram, intent.getMultipartExtraValue());
//		
//		if(stringBody != null || fileBody != null || uriBody != null) {
//			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//			if(stringBody != null) {
//				entity.addPart(extraPram, stringBody);
//			}
//			if(uriBody != null) {
//				entity.addPart(uriParamName, uriBody);
//			}
//			if(fileBody != null) {
//				entity.addPart(fileParamName, fileBody);
//			}
//			post.setEntity(entity);
//		}
//	}
	
//	private FileBody getFileBodyFromUri(String uri, String paramName) {
//		if(TextUtils.isEmpty(paramName) || TextUtils.isEmpty(uri)) {
//			return null;
//		}
//		File f = null;
//		try {
//			f = new File(new URI(uri));
//		} catch (URISyntaxException e) {
//			if(verboseLoggingEnabled()) {
//				v("file not found " + uri);
//			} 
//		}
//		return new FileBody(f, ENCODING);
//	}
//	
//	private FileBody getFileBodyFromFile(String file, String paramName) {
//		if(TextUtils.isEmpty(file) || TextUtils.isEmpty(paramName)) {
//			return null;
//		}
//		return new FileBody(new File(file), ENCODING);
//	}
//	
//	private StringBody getStringBody(String param, String value) {
//		if(TextUtils.isEmpty(param)) {
//			return null;
//		}
//		if(value == null) {
//			value = "";
//		}
//		StringBody body = null;
//		try  {
//			body = new StringBody(value);
//		} catch(Throwable t) {
//			v(t.getMessage());
//		}
//		return body;
//	}
	
	private void logAndThrow(String msg) {
		if(errorLoggingEnabled()) {
			e(msg);
		}
		throw new ProviderException(msg);
	}
	
}