package org.chickymate.gu.activity;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.binder.FromHtmlViewBinder;
import org.chickymate.gu.utils.Gu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Feedbacks extends BaseListActivity {
	

	public static final String[] FROM = new String[] {
			Gu.SearchResultsModel.Column.title,
			Gu.SearchResultsModel.Column.description,
			Gu.SearchResultsModel.Column.url, Gu.AnalitycModel.Column.id };

	public static final int[] TO = new int[] { 
			R.id.feedback_title,
			R.id.feedback_description, 
			R.id.feedback_result_url };

	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedbacks);
		
		cursor = this.managedQuery(Gu.Feedback.URI, new String[]{"feedback", "visited", "title",  "url", "description", Gu.AnalitycModel.Column.id},
				Gu.AnalitycModel.Column.feedback + " = ? ", new String[]{"1"}, "url");	
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.activity_feedback_item, cursor, FROM, TO);
		adapter.setViewBinder(new FromHtmlViewBinder());
		setListAdapter(adapter);
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getContentResolver()
				.unregisterContentObserver(contentObserver);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		trackFeedbacksView();
		getContentResolver().registerContentObserver(Gu.AnalitycModel.URI, true, contentObserver);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		trackOnClickFeedbackItemEvent(position);
		Intent i = new Intent(Intent.ACTION_VIEW);
		TextView urlTextView = (TextView) v
				.findViewById(R.id.feedback_result_url);
		i.setData(Uri.parse(urlTextView.getText().toString()));
		startActivity(i);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		int itemId = Menu.FIRST;
		menu.add(0,itemId,itemId++,R.string.remove_feedback);
		menu.setHeaderTitle(R.string.manage_feedback);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo adapterMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		TextView urlView = (TextView)adapterMenuInfo.targetView.findViewById(R.id.feedback_result_url);
		CharSequence url = urlView.getText();
		setItemFeedBackToFalse(url.toString());
		return super.onContextItemSelected(item);

	}
	
	public void setItemFeedBackToFalse(String url) {
		ContentValues values = new ContentValues();
		values.put(Gu.AnalitycModel.Column.feedback, 0);
		getContentResolver().update(Gu.AnalitycModel.URI, values,
				Gu.AnalitycModel.Column.url + " = ?", new String[] { url });
	}
	
	//=========================================================
	// Observers
	//=========================================================
	
	private ContentObserver contentObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			//use on the adapter SimpleAdapter.notifyDataSetChanged() instead of requiry
			cursor.requery();
		}
	};

}
