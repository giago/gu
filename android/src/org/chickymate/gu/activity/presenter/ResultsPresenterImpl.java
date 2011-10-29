package org.chickymate.gu.activity.presenter;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.ResultsSearchActivityCoordinatorImpl;
import org.chickymate.gu.activity.view.ResultView;
import org.chickymate.gu.service.GuService;
import org.chickymate.gu.utils.Gu;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsPresenterImpl extends BasePresenter implements
		ResultsPresenter {

	protected static final IntentFilter NETWORK_ERROR_INTENT_FILTER = new IntentFilter(
			Gu.NETWORK_ERROR_ACTION);
	private static final int THRESHOLD_TO_LOAD_NEXT = 5;
	private ResultsSearchActivityCoordinatorImpl activity;

	private ResultView resultView;
	private Cursor cursor;
	private String keywordId;
	private String searchTerm = null;
	private NetworkErrorReceiver networkErrorReceiver;

	private long lastClickedId = -1;
	private int lastClickedPosition;

	public ResultsPresenterImpl(ResultView resultView) {
		super();
		this.resultView = resultView;
	}

	@Override
	public void onCreate(ResultsSearchActivityCoordinatorImpl activity, Intent intent) {
		this.activity = activity;
		this.networkErrorReceiver = new NetworkErrorReceiver();
	}

	@Override
	public void onResume() {
		activity.trackResultView();
		activity.getContentResolver().registerContentObserver(
				Gu.SearchResultsModel.URI, true, contentObserver);
		activity.getContentResolver().registerContentObserver(
				Gu.AnalitycModel.URI, true, contentObserver);
		activity.registerReceiver(networkErrorReceiver,
				NETWORK_ERROR_INTENT_FILTER);
	}

	@Override
	public void onPause() {
		activity.getContentResolver()
				.unregisterContentObserver(contentObserver);
		activity.unregisterReceiver(networkErrorReceiver);
	}

	@Override
	public void executeSearch(String keywordId) {
		this.keywordId = keywordId;
		initCursor(keywordId);
		resultView.refreshView(cursor);
	}

	@Override
	public void onItemClick(ListView l, View v, int position, long id) {
		activity.trackOnClickSaveFeedbackEvent(position);
		String url = (String) v.getTag();
		markItemAsVisited(url);
		Intent i = new Intent(Intent.ACTION_VIEW);
		TextView urlTextView = (TextView) v
				.findViewById(R.id.list_item_result_url);
		i.setData(Uri.parse(urlTextView.getText().toString()));
		activity.startActivity(i);
	}

	@Override
	public void getNextPage(int pos) {
		if (pos <= 0 || pos >= Gu.MAXIMUN_NUMBER_OF_RESULTS) {
			return;
		}
		if (pos + THRESHOLD_TO_LOAD_NEXT < cursor.getCount()) {
			return;
		}
		pos++;
		if (hasReachNextPage(pos)) {
			int page = pos / Gu.GOOGLE_API_PAGE_SIZE;
			activity.startService(GuService.Builder.search(getSearchTerm(),
					keywordId, page));
			resultView.startLoadingAnimation();
		}
	}

	private boolean hasReachNextPage(int pos) {
		long currentPage = ((long) pos) % ((long) Gu.GOOGLE_API_PAGE_SIZE);
		if (currentPage == 0) {
			return true;
		}
		return false;
	}

	private void initCursor(String keywordId) {
		if (keywordId != null) {
			cursor = activity.managedQuery(Gu.SearchResultsModel.URI, null,
					Gu.SearchResultsModel.Column.keywordId + " = ?",
					new String[] { keywordId }, null);
			if (cursor.getCount() == 0) {
				activity.startService(GuService.Builder.search(getSearchTerm(),
						keywordId));
			} else {
				resultView.stopLoadingAnimation();
			}
		}
	}

	private String getSearchTerm() {
		if (searchTerm == null) {
			Cursor c = activity.managedQuery(Gu.KeywordsModel.URI, null,
					"_id = ?", new String[] { keywordId }, null);
			if (c.moveToFirst()) {
				searchTerm = Gu.KeywordsModel.Access.searchterm(c);
			}
		}
		return searchTerm;
	}

	private void markItemAsVisited(String url) {
		Cursor c = activity.managedQuery(Gu.AnalitycModel.URI, null,
				Gu.AnalitycModel.Column.url + " = ? ", new String[] { url },
				null);
		ContentValues values = new ContentValues();
		values.put(Gu.AnalitycModel.Column.visited, 1);
		values.put(Gu.AnalitycModel.Column.feedback, 0);
		values.put(Gu.AnalitycModel.Column.url, url);

		if (!c.moveToNext()) {
			activity.getContentResolver().insert(Gu.AnalitycModel.URI, values);
		}
	}
	
	@Override
	public void onClick(View v) {
		Button clickedButton = (Button) v;
		String url = (String) clickedButton.getTag();
		setItemFeedBack(url);
		clickedButton.setVisibility(Button.GONE);
		((LinearLayout)clickedButton.getParent()).findViewById(R.id.list_item_feedback_message).setVisibility(TextView.VISIBLE);		
	}

	@Override
	public void setItemFeedBack(String url) {
		ContentValues values = new ContentValues();
		values.put(Gu.AnalitycModel.Column.feedback, 1);
		activity.getContentResolver().update(Gu.AnalitycModel.URI, values,
				Gu.AnalitycModel.Column.url + " = ?", new String[] { url });
	}

	@Override
	public void goToGoogle() {
		activity.trackOnClickGoogleSearchEvent();
		Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		String term = getSearchTerm();
		intent.putExtra(SearchManager.QUERY, term);
		activity.startActivity(intent);
	}

	@Override
	public void goToHome() {
		activity.finish();
	}

	// =========================================================
	// Observers
	// =========================================================

	private ContentObserver contentObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			// use on the adapter SimpleAdapter.notifyDataSetChanged() instead
			// of requiry
			cursor.requery();
			resultView.stopLoadingAnimation();
		}
	};

	private class NetworkErrorReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context,
					intent.getStringExtra(Gu.Extra.networkMessage),
					Toast.LENGTH_LONG).show();
			resultView.stopLoadingAnimation();
		}
	}

	@Override
	public long getLastClickedId() {
		return lastClickedId;
	}

	@Override
	public void setLastClickedId(long id) {
		this.lastClickedId = id;
	}

	@Override
	public int getLastClickedPosition() {
		return this.lastClickedPosition;
	}

	@Override
	public void setLastClickedPosition(int position) {
		this.lastClickedPosition = position;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
	}
}
