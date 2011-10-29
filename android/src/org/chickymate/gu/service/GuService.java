package org.chickymate.gu.service;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import novoda.lib.httpservice.HttpService;
import novoda.lib.httpservice.util.IntentBuilder;

import org.chickymate.gu.utils.Gu;

import android.content.Intent;


public class GuService extends HttpService {
	
private static final String ACTION = "org.chickymate.gu.HTTP_REQUEST";
	
	//private static final String URL = "http://www.google.com/m/search?q=";
	
	private static final String URL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8";
	
	public static class Builder {
		
		public static final Intent search(String searchterm, String id) {
			return search(searchterm, id, 0);
		}
		
		public static final Intent search(String searchterm, String id, int page) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("q", Gu.KeywordsModel.encodeSearchterm(searchterm));
			map.put("start", "" + page*8);
			
			Intent intent = new IntentBuilder(ACTION, buildUrl(map.entrySet())).build();
			intent.putExtra(Gu.SearchResultsModel.Column.keywordId, id);
			return intent;
		}
		
		private static final String buildUrl(Set<Entry<String, String>> params){
			StringBuilder sb = new StringBuilder(URL);
			for(Entry<String, String> param:params) {
				sb.append("&").append(param.getKey()).append("=").append(param.getValue());
			}
			return sb.toString();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		add(new GoogleHandler(this));
	}

}
