package org.chickymate.gu.activity.view;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;
import org.chickymate.gu.activity.presenter.SearchPresenter;
import org.chickymate.gu.activity.presenter.SearchPresenterImpl;
import org.chickymate.gu.utils.Log;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class SearchViewImpl implements SearchView {

	private static final int[] TO = new int[] { R.id.list_item_result_title };

	private SearchPresenter presenter;
	private EditText et;
	private ListView listView;

	private ResultsSearchActivityCoordinatorImpl activity;

	public SearchViewImpl(ResultsSearchActivityCoordinatorImpl activity) {
		super();
		this.activity = activity;
		presenter = new SearchPresenterImpl(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		et = (EditText) activity.findViewById(R.id.search_term);
		listView = (ListView) activity
				.findViewById(R.id.activity_search_terms_list);
		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				presenter.onSearchTermChange();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		et.setFocusableInTouchMode(true);
		
		et.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.dev("onClick");
				activity.showKeywords();
			}
		});
		presenter.onCreate();
	}

	@Override
	public void onResume() {
		activity.trackResultView();
		presenter.onResume();
	}

	@Override
	public void setSearchterm(String text) {
		et.setText(text);
		et.setSelection(et.getText().length());
	}

	@Override
	public String getSearchterm() {
		CharSequence cs = et.getText();
		if (!TextUtils.isEmpty(cs)) {
			return cs.toString();
		}
		return null;
	}

	@Override
	public void updateSearchForm() {
		Button b = (Button) activity.findViewById(R.id.search_submit);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.trackFeedbacksView();
				showResults();
			}
		});
	}

	@Override
	public ResultsSearchActivityCoordinatorImpl getActivity() {
		return activity;
	}

	@Override
	public void updateSearchTermList() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				activity.trackOnClickRecentTermEvent(position);
				presenter.onRecentTermClick(id);
			}
		});
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(activity,
				R.layout.activity_search_term_item, presenter.getCursor(),
				SearchPresenter.FROM, TO);
		adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Cursor cursor,
					int columnIndex) {
				String value = cursor.getString(columnIndex);
				String searchterm = getSearchterm();
				if (!TextUtils.isEmpty(searchterm) && !TextUtils.isEmpty(value)) {
					String htmlVersion = value.replace(searchterm, searchterm
							+ "<b>");
					((TextView) view).setText(Html.fromHtml(htmlVersion
							+ "</b>"));
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(adapter);
	}

	private void showResults() {
		String cs = getSearchterm();
		if (!TextUtils.isEmpty(cs)) {
			presenter.search(cs);
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSearchRequested() {
		et.setSelection(et.getText().length());
		((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(et, 2);
		return true;
	}

	@Override
	public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

}