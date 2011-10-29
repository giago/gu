package org.chickymate.gu.activity;

import org.chickymate.gu.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionMenu  {
	static final int INFO_DIALOG_ID = 0;
	
	private final Activity activity;

	public OptionMenu(Activity activity) {
		super();
		this.activity = activity;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = activity.getMenuInflater();
		inflater.inflate(R.menu.gu_menu, menu);
		return true;
	}

	public Boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.info:
			activity.showDialog(INFO_DIALOG_ID);
			return true;
		case R.id.feedbacklist :
			activity.startActivity(buildIntent());
			return true;
		default:
			return null;
		}
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case INFO_DIALOG_ID:
			dialog = new Dialog(activity);

			dialog.setContentView(R.layout.info_dialog);
			dialog.setTitle(activity.getResources()
					.getString(R.string.info_dialog_title));

			break;
			
		default:
			dialog = null;
		}

		return dialog;
	}
	
	private Intent buildIntent() {
		Intent intent = new Intent(activity, Feedbacks.class);
		return intent;
	}
}
