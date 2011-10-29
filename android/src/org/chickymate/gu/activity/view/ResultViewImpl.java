package org.chickymate.gu.activity.view;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;
import org.chickymate.gu.activity.adapter.ResultsCursorAdapter;
import org.chickymate.gu.activity.binder.FromHtmlViewBinder;
import org.chickymate.gu.activity.presenter.ResultsPresenter;
import org.chickymate.gu.activity.presenter.ResultsPresenterImpl;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ResultViewImpl implements ResultView {

	public static final int[] TO = new int[] { R.id.list_item_result_title,
		R.id.list_item_result_description, R.id.list_item_result_url };

	private ResultsPresenter presenter ;

	private static final String LAST_CLICKED_ID = "lastClickedId";
	private static final String LAST_CLICKED_POSITION = "lastClickedPosition";
	
	private ListView resultList ;
	
	private ResultsSearchActivityCoordinatorImpl activity;
		
	public ResultViewImpl(ResultsSearchActivityCoordinatorImpl activity) {
		super();
		this.activity = activity;
		presenter = new ResultsPresenterImpl(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		resultList = (ListView) activity.findViewById(R.id.result_list);
		resultList.addHeaderView(activity.getLayoutInflater().inflate(R.layout.activity_results_header, null));
		resultList.addFooterView(activity.getLayoutInflater().inflate(R.layout.activity_results_footer, null));
		
		presenter.onCreate(activity, activity.getIntent());
	}

	@Override
	public void onResume() {
		activity.trackResultView();
		presenter.onResume();
		resultList.setSelection(presenter.getLastClickedPosition());
	}
	
	@Override
	public void onPause(){		
		presenter.onPause();
	}

	@Override
	public void refreshView(Cursor c) {
		resultList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int position, long id) {
						onListItemClick(view, position, id);
					}
				});
		SimpleCursorAdapter adapter = new ResultsCursorAdapter(activity, c, ResultsPresenterImpl.FROM, TO, presenter);
		adapter.setViewBinder(new FromHtmlViewBinder());
		resultList.setAdapter(adapter);
	}

	@Override
	public void stopLoadingAnimation() {
		activity.findViewById(R.id.footer).setVisibility(View.GONE);
		
	}

	@Override
	public void startLoadingAnimation() {
		activity.findViewById(R.id.footer).setVisibility(View.VISIBLE);
		
	}

	@Override
	public void executeSearch(String keywordId) {
		presenter.executeSearch(keywordId);
	}
	
	private void onListItemClick( View v, int position, long id) {
		presenter.setLastClickedId(id);
		presenter.setLastClickedPosition(position);
		if(v.findViewById(R.id.header) != null) {
			presenter.goToGoogle();
			return;
		}
		if(v.findViewById(R.id.footer) != null) {
			return;
		}
		activity.trackOnClickResultEvent(position);
		presenter.onItemClick(resultList, v, position, id);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putLong(LAST_CLICKED_ID, presenter.getLastClickedId());
		outState.putInt(LAST_CLICKED_POSITION, presenter.getLastClickedPosition());
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		presenter.setLastClickedId(savedInstanceState.getLong(LAST_CLICKED_ID));
		presenter.setLastClickedPosition(savedInstanceState.getInt(LAST_CLICKED_POSITION));
	}

	@Override
	public ResultsSearchActivityCoordinatorImpl getActivity() {
		return this.activity;
	}

}
