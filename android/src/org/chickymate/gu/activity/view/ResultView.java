package org.chickymate.gu.activity.view;

import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;

import android.database.Cursor;

public interface ResultView  extends View {
	void refreshView(Cursor c);
	void stopLoadingAnimation() ;
	void startLoadingAnimation();
	void executeSearch(String keywordId);
	
	ResultsSearchActivityCoordinatorImpl getActivity();
}
