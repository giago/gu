package org.chickymate.gu.service;

import novoda.lib.httpservice.SimpleRequestHandler;
import novoda.lib.httpservice.provider.IntentWrapper;
import novoda.lib.httpservice.provider.Response;

import org.chickymate.gu.service.model.GoogleResponse;
import org.chickymate.gu.service.model.Result;
import org.chickymate.gu.utils.Gu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;


public class GoogleHandler extends SimpleRequestHandler {

	private Context context;
	private Gson gson;

	public GoogleHandler(Context context) {
		this.context = context;
		gson = new Gson();
	}

	@Override
	public boolean match(IntentWrapper request) {
		return true;
	}

	@Override
	public void onContentReceived(IntentWrapper intentWrapper, Response response) {
		super.onContentReceived(intentWrapper, response);
		String searchId = intentWrapper.getIntent().getStringExtra(Gu.SearchResultsModel.Column.keywordId);
		GoogleResponse data = gson.fromJson(response.getContentAsString(), GoogleResponse.class);
		if(data == null) {
			sendNetworkError("No data return from server!");
			return;
		}
		if(data.responseData == null) {
			sendNetworkError("No data response return from server!");
			return;
		}
		if(data.responseData.results.isEmpty()) {
			sendNetworkError("No results, try google!");
		}
		for(Result result : data.responseData.results) {
			ContentValues cv = new ContentValues();
			cv.put(Gu.SearchResultsModel.Column.title, result.titleNoFormatting);
			cv.put(Gu.SearchResultsModel.Column.description, result.content);
			cv.put(Gu.SearchResultsModel.Column.url, result.url);
			cv.put(Gu.SearchResultsModel.Column.keywordId, searchId);
			context.getContentResolver().insert(Gu.SearchResultsModel.URI, cv);
		}
	}
	
	@Override
	public void onThrowable(IntentWrapper intentWrapper, Throwable t) {
		sendNetworkError("The services are not responding, check the network or retry later");
	}
	
	private void sendNetworkError(String message) {
		Intent intent = new Intent(Gu.NETWORK_ERROR_ACTION);
		intent.putExtra(Gu.Extra.networkMessage, message);
		context.sendBroadcast(intent);
	}

}
