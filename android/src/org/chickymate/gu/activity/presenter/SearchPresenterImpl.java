package org.chickymate.gu.activity.presenter;

import org.chickymate.gu.activity.view.SearchView;
import org.chickymate.gu.activity.view.SearchViewImpl;
import org.chickymate.gu.service.GuService;
import org.chickymate.gu.utils.Gu;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class SearchPresenterImpl extends BasePresenter implements SearchPresenter {

	private static final String[] PROJECTION = new String[]{Gu.KeywordsModel.Column.id, Gu.KeywordsModel.Column.keywordTerm}; 
	private SearchView searcView;
	private Cursor cursor;
	private String searchterm;
	
	public SearchPresenterImpl(SearchViewImpl searcView) {
		this.searcView = searcView;
	}
	
	@Override
	public void onCreate() {
		searcView.updateSearchForm();
		searcView.updateSearchTermList();
	}

	@Override
	public void search(String searchterm) {
		deleteOldData();
		searchterm = searchterm.trim();
		// check if i have already in the db the searchterm
		Cursor c = searcView.getActivity().managedQuery(Gu.KeywordsModel.URI, PROJECTION, Gu.KeywordsModel.Column.keywordTerm + " = ?", new String[]{searchterm}, null);
		String id = null;
		if(c.moveToFirst()) {
			id = Gu.KeywordsModel.Access.id(c);
		} else {
			// the db do not contain the serach key, it's the first time it is used, so the keyword is memorized in the db
			ContentValues cv = new ContentValues();
			cv.put(Gu.KeywordsModel.Column.keywordTerm, searchterm);
			
			Uri uri = searcView.getActivity().getContentResolver().insert(Gu.KeywordsModel.URI, cv);
			id = uri.getLastPathSegment();
			searcView.getActivity().startService(GuService.Builder.search(searchterm, id));
		}
		
		if(!TextUtils.isEmpty(id)) {
			searcView.getActivity().executeSearch(id);
		}
	}
	
	private void deleteOldData() {
		String timestamp = "" + (System.currentTimeMillis() - Gu.DATA_REFRESH_PERIOD);
		searcView.getActivity().getContentResolver().delete(Gu.KeywordsModel.URI, Gu.KeywordsModel.Column.timestap + " < ?", new String[] {timestamp});
	}

	@Override
	public Cursor getCursor() {
		String selection = null;
		if(searchterm != null) {
			selection = Gu.KeywordsModel.Column.keywordTerm + " like \"" + searchterm + "%\"";
		}
		cursor = searcView.getActivity().managedQuery(Gu.KeywordsModel.URI, PROJECTION, selection, null, Gu.KeywordsModel.Column.keywordTerm);
		return cursor;
	}

	@Override
	public void onRecentTermClick(long id) {
		//TODO
		Cursor c = searcView.getActivity().managedQuery(Gu.KeywordsModel.URI, PROJECTION, "_id = ?", new String[]{"" + id}, null);
		//
		String currentSearchTerm = searcView.getSearchterm();
		if(c.moveToFirst()) {
			String searchterm = Gu.KeywordsModel.Access.searchterm(c);
			if(TextUtils.isEmpty(currentSearchTerm)) {
				searcView.setSearchterm(searchterm);
			} else if(currentSearchTerm.equals(searchterm)) {
				search(searchterm);
			} else {
				searcView.setSearchterm(searchterm);
			}
		}
	}

	@Override
	public void onSearchTermChange() {
		searchterm = searcView.getSearchterm();
		searcView.updateSearchTermList();
	}

	@Override
	public void onResume() {
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}
}