package org.chickymate.gu.activity.presenter;

import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;
import org.chickymate.gu.utils.Gu;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public interface ResultsPresenter extends Presenter, View.OnClickListener  {
	
	public static final String[] FROM = new String[] {
		Gu.SearchResultsModel.Column.title,
		Gu.SearchResultsModel.Column.description,
		Gu.SearchResultsModel.Column.url, Gu.SearchResultsModel.Column.id };

	void onCreate(ResultsSearchActivityCoordinatorImpl activity, Intent intent);

	void onItemClick(ListView l, View v, int position, long id);

	void setItemFeedBack(String itemId);

	void getNextPage(int pos);
	
	void goToHome();

	void goToGoogle();
	
	long getLastClickedId();
	
	void setLastClickedId(long id);
	
	int getLastClickedPosition();
	
	void setLastClickedPosition(int position);

	void executeSearch(String keywordId);
	
}
