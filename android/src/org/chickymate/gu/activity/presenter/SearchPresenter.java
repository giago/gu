package org.chickymate.gu.activity.presenter;

import org.chickymate.gu.utils.Gu;

import android.database.Cursor;


public interface SearchPresenter extends Presenter {

	public static final String[] FROM = new String[] { Gu.KeywordsModel.Column.keywordTerm };

	void search(String searchTerm);

	Cursor getCursor();

	void onRecentTermClick(long id);

	void onSearchTermChange();

}
