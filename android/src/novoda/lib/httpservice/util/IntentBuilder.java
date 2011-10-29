
package novoda.lib.httpservice.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import novoda.lib.httpservice.provider.IntentWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.ResultReceiver;

/**
 * This class is responsible to help in setting all the necessary request
 * information on the intent so that the ReqeustReader can on the other side
 * extract the request object from the intent. A simple intent can be build like
 * : new RequestWriter(URI).build() -> is a simple get on the specified uri
 * 
 * @author luigi@novoda.com
 */
public class IntentBuilder {

    private Intent intent;

    private ArrayList<ParcelableBasicNameValuePair> requestParameters = new ArrayList<ParcelableBasicNameValuePair>();

    public IntentBuilder(String action, String url) {
        this(action, Uri.parse(url));
    }

    public IntentBuilder(String action, Uri uri) {
        intent = new Intent(action, uri);
    }

    public IntentBuilder asPost() {
        return method(IntentWrapper.Method.POST);
    }

    private IntentBuilder method(int method) {
        intent.putExtra(IntentWrapper.Extra.method, method);
        return this;
    }

    public IntentBuilder withHandlerKey(String handlerKey) {
        intent.putExtra(IntentWrapper.Extra.handler_key, handlerKey);
        return this;
    }

    public IntentBuilder withParams(Map<String, String> parameters) {
        for (Entry<String, String> entry : parameters.entrySet()) {
            requestParameters.add(new ParcelableBasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    public IntentBuilder withParam(String key, String value) {
        requestParameters.add(new ParcelableBasicNameValuePair(key, value));
        return this;
    }

    public IntentBuilder withParams(ArrayList<ParcelableBasicNameValuePair> params) {
        requestParameters.addAll(params);
        return this;
    }

    public IntentBuilder withStringResultReceiver(ResultReceiver receiver) {
    	if(receiver == null) {
    		return this;
    	}
        intent.putExtra(IntentWrapper.Extra.result_receiver, receiver);
        return this;
    }
    
    public IntentBuilder withConsumedResultReceiver(ResultReceiver receiver) {
    	if(receiver == null) {
    		return this;
    	}
        intent.putExtra(IntentWrapper.Extra.result_consumed_receiver, receiver);
        return this;
    }

    public Intent build() {
        ArrayList<ParcelableBasicNameValuePair> list = new ArrayList<ParcelableBasicNameValuePair>(
                Collections.unmodifiableList(requestParameters)
        );
        intent.putParcelableArrayListExtra(IntentWrapper.Extra.params, list);
        intent.putExtra(IntentWrapper.Extra.uid, System.nanoTime());
        return intent;
    }

	public IntentBuilder withDisableCache() {
		intent.putExtra(IntentWrapper.Extra.cache_disabled, true);
        return this;
	}

	public IntentBuilder withMiltipartFile(String paramName, String file) {
		intent.putExtra(IntentWrapper.Extra.multipart_file_name, paramName);
		intent.putExtra(IntentWrapper.Extra.multipart_file, file);		
		return this;
	}

	public IntentBuilder withMiltipartUri(String paramName, String uri) {
		intent.putExtra(IntentWrapper.Extra.multipart_uri_name, paramName);
		intent.putExtra(IntentWrapper.Extra.multipart_uri, uri);
		return this;
	}
	
	public IntentBuilder withMultipartExtra(String extraParam, String extraValue) {
		if(extraParam != null && extraValue != null) {
			intent.putExtra(IntentWrapper.Extra.multipart_extra_value, extraValue);
		}
		return this;
	}
	
}
