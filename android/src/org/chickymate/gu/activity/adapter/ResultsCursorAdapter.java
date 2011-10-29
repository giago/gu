package org.chickymate.gu.activity.adapter;

import org.chickymate.gu.R;
import org.chickymate.gu.activity.presenter.ResultsPresenter;
import org.chickymate.gu.utils.Gu;
import org.chickymate.gu.utils.Gu.SearchResultsModel.Column;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ResultsCursorAdapter extends SimpleCursorAdapter {
	private ResultsPresenter presenter;
	
	public ResultsCursorAdapter(Context context, Cursor c, String[] from, int[] to, ResultsPresenter presenter) {
		super(context, R.layout.activity_results_result_item, c,
				from, to);
		this.presenter = presenter; 
	}

	@Override
	public View getView(int pos, View v, ViewGroup p) {
		presenter.getNextPage(pos);
		return super.getView(pos, v, p);
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		super.bindView(view, context, c);
		view.setTag(c.getString(c.getColumnIndex(Column.url)));
		Button b = (Button) view
				.findViewById(R.id.list_item_result_feedback_button);
		b.setTag(c.getString(c.getColumnIndex(Column.url)));
		b.setOnClickListener(presenter);
		/*
		 * TODO too many if, and the if conditions are difficult to undertsand,
		 * create methods with meaningfull name as isVisited() ....
		 */
		if (c.getInt(c.getColumnIndex(Gu.AnalitycModel.Column.visited)) == 1) {
			((TextView) view.findViewById(R.id.list_item_result_title))
					.setTextColor(view.getResources().getColor(R.color.siteVisited));
			
			if (c.getInt(c.getColumnIndex(Gu.AnalitycModel.Column.feedback)) == 0
					&& presenter.getLastClickedId() == c.getInt(c.getColumnIndex(Column.id))) {
				b.setVisibility(Button.VISIBLE);
				view.findViewById(R.id.list_item_feedback_message)
						.setVisibility(TextView.GONE);

			} else if (c.getInt(c
					.getColumnIndex(Gu.AnalitycModel.Column.feedback)) == 1) {
				b.setVisibility(Button.GONE);
				view.findViewById(R.id.list_item_feedback_message)
						.setVisibility(TextView.VISIBLE);
			} else {
				b.setVisibility(Button.GONE);
				view.findViewById(R.id.list_item_feedback_message)
						.setVisibility(TextView.GONE);
			}
		} else {
			((TextView) view.findViewById(R.id.list_item_result_title))
					.setTextColor(view.getResources().getColor(
							R.color.siteNotVisited));
			b.setVisibility(Button.GONE);
			view.findViewById(R.id.list_item_feedback_message).setVisibility(
					TextView.GONE);
		}
	}

}
