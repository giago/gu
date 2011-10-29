package org.chickymate.gu.activity;

import org.chickymate.gu.utils.Gu;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {

	private OptionMenu optionMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		optionMenu = new OptionMenu(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return optionMenu.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Boolean result =  optionMenu.onOptionsItemSelected(item);
		if(result != null){
			return result;
		}else{
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
			return optionMenu.onCreateDialog(id);
	}
	
	//This is a copy of what is in the BaseActivity!!!
	

	
	
	private GoogleAnalyticsTracker tracker;
	
	protected GoogleAnalyticsTracker getTracker() {
		if(tracker == null) {
			tracker = GoogleAnalyticsTracker.getInstance();
			tracker.start(Gu.Analytics.UA_ACCOUNT_CODE, 20, this);
		}
		return tracker;
	}
	
	public void trackSearchView() {
		getTracker().trackPageView(Gu.Analytics.SEARCH_ACTIVITY);
	}
	
	public void trackResultView() {
		getTracker().trackPageView(Gu.Analytics.RESULTS_ACTIVITY);
	}
	
	public void trackFeedbacksView() {
		getTracker().trackPageView(Gu.Analytics.FEEDBACKS_ACTIVITY);
	}
	
	public void trackOnClickGoogleSearchEvent() {
		trackClickEvent(Gu.Analytics.GOOGLE_LABEL, Gu.Analytics.GOOGLE_VALUE);
	}
	
	public void trackOnClickSearchEvent() {
		trackClickEvent(Gu.Analytics.SEARCH_LABEL, Gu.Analytics.SEARCH_VALUE);
	}
	
	public void trackOnClickResultEvent(int position) {
		trackClickEvent(Gu.Analytics.RESULT_LABEL, position);
	}
	
	public void trackOnClickSaveFeedbackEvent(int position) {
		trackClickEvent(Gu.Analytics.SAVE_FEEDBACK_LABEL, position);
	}
	
	public void trackOnClickFeedbackItemEvent(int position) {
		trackClickEvent(Gu.Analytics.FEEDBACK_LABEL, position);
	}
	
	public void trackOnClickRecentTermEvent(int position) {
		trackClickEvent(Gu.Analytics.RECENT_TERM_LABEL, position);
	}
	
	private void trackClickEvent(String label, int value){
		getTracker().trackEvent(Gu.Analytics.CATEGORY, Gu.Analytics.ACTION, 
				label, value);
	}
	
	@Override
	public void onDestroy() {
		if(tracker != null) {
			tracker.stop();
		}
		super.onDestroy();
	}

}
