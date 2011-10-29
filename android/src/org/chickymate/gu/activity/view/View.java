package org.chickymate.gu.activity.view;

import android.app.Activity;
import android.os.Bundle;

public interface View {
	
	public void onCreate(Bundle savedInstanceState);
	
	void onResume();

	void onPause();

	void onSaveInstanceState(Bundle outState);

	void onRestoreInstanceState(Bundle savedInstanceState);
	
	Activity getActivity();

}
