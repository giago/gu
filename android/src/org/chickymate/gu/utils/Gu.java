package org.chickymate.gu.utils;

import java.net.URLEncoder;

import android.database.Cursor;
import android.net.Uri;

public class Gu {

	public static final String AUTHORITY = "org.chickymate.gu";
	private static final String CONTENT_PREFIX = "content://" + AUTHORITY + "/";
	public static final int GOOGLE_API_PAGE_SIZE = 8;
	public static final int MAXIMUN_NUMBER_OF_PAGES = 8;
	public static final int MAXIMUN_NUMBER_OF_RESULTS = (MAXIMUN_NUMBER_OF_PAGES - 1)
			* GOOGLE_API_PAGE_SIZE;
	
	public static final long DATA_REFRESH_PERIOD = 7L*24L*3600L*1000L;
	public static final String NETWORK_ERROR_ACTION = "com.chickymate.gu.NETWORK_ERROR_ACTION";
	public static final String KEYWORDS_PAGE_LIMIT = "100";
	public static final String W3C_URL_FORM_ENCODING = "UTF-8";

	public static interface Extra {
		String keywordId = Gu.SearchResultsModel.Column.keywordId;
		String networkMessage = "networkMessage";
	}

	public static interface Database {
		String NAME = AUTHORITY + ".db";
		int VERSION = 29;
	}

	public static class KeywordsModel {

		public static final String NAME = "keyword";
		public static final Uri URI = Uri.parse(CONTENT_PREFIX + NAME);

		public static final String CREATE_STM = "create table if not exists keyword(_id integer primary key autoincrement, "
				+ "keywordterm text, timestamp integer);";
		public static final String DROP_STM = "drop table if exists keyword;";


		public static interface Column {
			String id = "_id";
			String keywordTerm = "keywordterm";
			String timestap = "timestamp";
		}

		public static class Access {

			public static final String id(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.id));
			}

			public static final String searchterm(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.keywordTerm));
			}
			
			public static final long timestamp(android.database.Cursor c) {
				return c.getLong(c.getColumnIndex(Column.timestap));
			}
		}

		public static final String encodeSearchterm(String searchterm) {
			try {
				return URLEncoder.encode(searchterm, Gu.W3C_URL_FORM_ENCODING);
			} catch(Exception e) {
				Log.e("Encoding exception", e);
				return "";
			}
		}
	}

	public static class AnalitycModel {

		public static final String NAME = "analityc";
		public static final Uri URI = Uri.parse(CONTENT_PREFIX + NAME);

		public static final String CREATE_STM = "create table if not exists "
				+ NAME
				+ "(_id integer primary key autoincrement, "
				+ " search_result_url text, visited integer default 0, feedback integer default -1);";
		public static final String DROP_STM = "drop table if exists " + NAME
				+ ";";

		public static interface Column {
			String id = "_id";
			String url = "search_result_url";
			String visited = "visited";
			String feedback = "feedback";
		}

		public static class Access {
			public static final String url(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.url));
			}

			public static final int visited(android.database.Cursor c) {
				return c.getInt(c.getColumnIndex(Column.visited));
			}

			public static final int feedback(android.database.Cursor c) {
				return c.getInt(c.getColumnIndex(Column.feedback));
			}

			public static int id(Cursor c) {
				return c.getInt(c.getColumnIndex(Column.id));
			}
		}
	}

	public static class SearchResultsModel {

		public static final String NAME = "searchresult";
		public static final Uri URI = Uri.parse(CONTENT_PREFIX + NAME);

		public static final String CREATE_STM = "create table if not exists searchresult(_id integer primary key autoincrement, "
				+ "keyword_id integer, title text, description text, url text);";
		public static final String DROP_STM = "drop table if exists searchresult;";
		
		public static final String CREATE_CLEAN_TRIGGER_STM = "create trigger clean_searchresult after delete on searchresult begin delete from searchresult where keyword_id = old._id; end;";
		
		public static final String DROP_CLEAN_TRIGGER_STM = "drop trigger if exists clean_searchresult";
		

		public static interface Column {
			String id = "_id";
			String keywordId = "keyword_id";
			String title = "title";
			String description = "description";
			String url = "url";
		}

		public static class Access {

			public static final int keywordId(android.database.Cursor c) {
				return c.getInt(c.getColumnIndex(Column.keywordId));
			}

			public static final String title(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.title));
			}

			public static final String description(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.description));
			}

			public static final String url(android.database.Cursor c) {
				return c.getString(c.getColumnIndex(Column.url));
			}

			public static int id(Cursor c) {
				return c.getInt(c.getColumnIndex(Column.id));
			}
		}
	}

	public static class Feedback{
		public static final String NAME = "feedback";
		public static final Uri URI = Uri.parse(CONTENT_PREFIX + NAME);

	}
	
	public static interface Analytics {
		String UA_ACCOUNT_CODE = "UA-12503056-6";
		
		String SEARCH_ACTIVITY = "/Search";
		String RESULTS_ACTIVITY = "/Results";
		String FEEDBACKS_ACTIVITY = "/Feedbacks";
		
		String CATEGORY = "Clicks";
		String ACTION = "Button";
		int GOOGLE_VALUE = 0;
		int SEARCH_VALUE = 0;

		String GOOGLE_LABEL = "google";
		String SEARCH_LABEL = "serch";
		String RESULT_LABEL = "result";
		String FEEDBACK_LABEL = "feedback";
		String SAVE_FEEDBACK_LABEL = "savefeedback";
		String RECENT_TERM_LABEL = "searchterm";
	}
}
