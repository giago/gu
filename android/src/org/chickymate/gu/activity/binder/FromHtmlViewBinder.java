package org.chickymate.gu.activity.binder;

import android.database.Cursor;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class FromHtmlViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		((TextView) view).setText(Html.fromHtml(cursor.getString(columnIndex)));
		return true;
	}

}
