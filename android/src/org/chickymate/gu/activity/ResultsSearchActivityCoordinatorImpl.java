package org.chickymate.gu.activity;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.view.ResultView;
import org.chickymate.gu.activity.view.ResultViewImpl;
import org.chickymate.gu.activity.view.SearchView;
import org.chickymate.gu.activity.view.SearchViewImpl;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ResultsSearchActivityCoordinatorImpl extends BaseActivity {
	
	private SearchView searchView;
	private ResultView resultView;
	
	private static final String SHOW_KEYWORDS = "showKeywords";
	private static final String LAST_SEARCH_ID = "lastDearchId";
	private String lastSearschId;
	private boolean showKeywords = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_results);
		
		searchView = new SearchViewImpl(this);
		searchView.onCreate(savedInstanceState);
		
		resultView = new ResultViewImpl(this);
		resultView.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		resultView.onPause();
		searchView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(showKeywords){
			showKeywords();
		}else{
			executeSearch(this.lastSearschId);
		}
		resultView.onResume();
		searchView.onResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(SHOW_KEYWORDS, this.showKeywords);
		outState.putString(LAST_SEARCH_ID, this.lastSearschId);
		resultView.onSaveInstanceState(outState);
		searchView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		this.showKeywords = savedInstanceState.getBoolean(SHOW_KEYWORDS);
		this.lastSearschId = savedInstanceState.getString(LAST_SEARCH_ID);
		resultView.onRestoreInstanceState(savedInstanceState);
		searchView.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
	public boolean onSearchRequested() {
		showKeywords();
		return searchView.onSearchRequested();
	}

	public void executeSearch(String id) {
		this.lastSearschId = id;
		searchView.hideKeyboard();
		showResults();
		resultView.executeSearch(id);
		
	}
	
	public void showResults(){
		this.showKeywords = false;
		findViewById(R.id.activity_search_terms_list).setVisibility(View.GONE);
		findViewById(R.id.result_list).setVisibility(View.VISIBLE);
	}
	
	public void showKeywords(){
		this.showKeywords = true;
		findViewById(R.id.result_list).setVisibility(View.GONE);
		findViewById(R.id.activity_search_terms_list).setVisibility(View.VISIBLE);
	}
}