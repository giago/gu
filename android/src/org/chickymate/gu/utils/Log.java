package org.chickymate.gu.utils;

public class Log {
	
	private static final String TAG = "gutest";
	
	public static final boolean verboseLoggingEnabled() {
		return android.util.Log.isLoggable(TAG, android.util.Log.ERROR);
	}
	
	public static final boolean errorLoggingEnabled() {
		return android.util.Log.isLoggable(TAG, android.util.Log.ERROR);
	}
	
	public static final void e(String msg) {
		android.util.Log.e(TAG, msg);
	}
	
	public static final void e(String msg, Throwable t) {
		android.util.Log.e(TAG, msg, t);
	}
	
	public static final void v(String msg) {
		android.util.Log.v(TAG, msg);
	}
	
	public static final void dev(String msg) {
		android.util.Log.v(TAG, "===================================");
		android.util.Log.v(TAG, msg);
		android.util.Log.v(TAG, "===================================");
	}

	public static void toImplement() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		if(stack != null && stack.length > 2) {
			Log.e("Function " + stack[3].getMethodName() + " for class " + stack[3].getClassName() + " is not implemented yet!");
		}
	}

}