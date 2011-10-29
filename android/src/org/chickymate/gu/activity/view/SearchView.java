package org.chickymate.gu.activity.view;

import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;


public interface SearchView extends View {

	void updateSearchForm();

	void updateSearchTermList();

	String getSearchterm();

	void setSearchterm(String searchterm);
	
	ResultsSearchActivityCoordinatorImpl getActivity();
	
	boolean onSearchRequested();

	void hideKeyboard();

}
