package org.chickymate.gu.provider;

import java.util.HashMap;

import org.chickymate.gu.utils.Gu;
import org.chickymate.gu.utils.Log;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class GuProvider extends ContentProvider {

	private GuUriMatcher matcher = new GuUriMatcher();

	private DatabaseManager databaseManager;

	private SQLiteDatabase database;

	private static HashMap<String, String> searchResultProjectionMap = new HashMap<String, String>();
	static {
		searchResultProjectionMap.put(Gu.SearchResultsModel.Column.id,
				Gu.SearchResultsModel.NAME + "."
						+ Gu.SearchResultsModel.Column.id );
		searchResultProjectionMap.put(Gu.SearchResultsModel.Column.keywordId,
				Gu.SearchResultsModel.Column.keywordId);
		searchResultProjectionMap.put(Gu.SearchResultsModel.Column.title,
				Gu.SearchResultsModel.Column.title);
		searchResultProjectionMap.put(Gu.SearchResultsModel.Column.description,
				Gu.SearchResultsModel.Column.description);
		searchResultProjectionMap.put(Gu.SearchResultsModel.Column.url,
				Gu.SearchResultsModel.Column.url);
		searchResultProjectionMap.put(Gu.AnalitycModel.Column.visited,
				Gu.AnalitycModel.Column.visited);
		searchResultProjectionMap.put(Gu.AnalitycModel.Column.feedback,
				Gu.AnalitycModel.Column.feedback);
	}
	
	private static HashMap<String, String> feedbacksProjectionMap = new HashMap<String, String>();
	static {
		feedbacksProjectionMap.put(Gu.SearchResultsModel.Column.id, "DISTINCT "
				+ Gu.SearchResultsModel.NAME + "." + Gu.SearchResultsModel.Column.title + " AS searchresult_id");
		
		feedbacksProjectionMap.put(Gu.SearchResultsModel.Column.title,
				Gu.SearchResultsModel.Column.title);
		feedbacksProjectionMap.put(Gu.SearchResultsModel.Column.description,
				Gu.SearchResultsModel.Column.description);
		feedbacksProjectionMap.put(Gu.SearchResultsModel.Column.url,
				Gu.SearchResultsModel.Column.url);
		feedbacksProjectionMap.put(Gu.AnalitycModel.Column.visited,
				Gu.AnalitycModel.Column.visited);
		feedbacksProjectionMap.put(Gu.AnalitycModel.Column.feedback,
				Gu.AnalitycModel.Column.feedback);
		feedbacksProjectionMap.put(Gu.AnalitycModel.Column.id,
				Gu.AnalitycModel.NAME + "." + 
				Gu.AnalitycModel.Column.id );
	}

	protected SQLiteDatabase getDataBase() {
		if (database == null) {
			databaseManager = new DatabaseManager(getContext());
			database = databaseManager.getWritableDatabase();
		}
		return database;
	}

	@Override
	public String getType(Uri uri) {
		String type = null;
		switch (matcher.match(uri)) {
		case GuUriMatcher.KEYWORD_INCOMING_COLLECTION: {
			type = GuUriMatcher.KEYWORD_COLLECTION_TYPE;
			break;
		}
		case GuUriMatcher.KEYWORD_INCOMING_ITEM: {
			type = GuUriMatcher.KEYWORD_ITEM_TYPE;
			break;
		}
		case GuUriMatcher.SEARCH_RESULT_INCOMING_COLLECTION: {
			type = GuUriMatcher.SEARCH_RESULT_COLLECTION_TYPE;
			break;
		}
		case GuUriMatcher.SEARCH_RESULT_INCOMING_ITEM: {
			type = GuUriMatcher.SEARCH_RESULT_ITEM_TYPE;
			break;
		}
		case GuUriMatcher.ANALITYC_INCOMING_COLLECTION: {
			type = GuUriMatcher.ANALITYC_COLLECTION_TYPE;
			break;
		}
		case GuUriMatcher.ANALITYC_INCOMING_ITEM: {
			type = GuUriMatcher.ANALITYC_ITEM_TYPE;
			break;
		}
		case GuUriMatcher.FEEDBACK_INCOMING_COLLECTION:{
			type =GuUriMatcher.FEEDBACK_COLLECTION_TYPE;
			break;
		}
		default: {
			Log.e("Problem with uri : " + uri);
			throw new RuntimeException("Problem with uri : " + uri);
		}
		}
		return type;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] args) {
		int rowAffected = 0;
        switch (matcher.match(uri)) {
            case GuUriMatcher.KEYWORD_INCOMING_COLLECTION: {
                rowAffected = database.delete(Gu.KeywordsModel.NAME, selection, args);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rowAffected;
	}

	@Override
	public Uri insert(Uri uri, ContentValues cv) {
		Uri result = null;
		switch (matcher.match(uri)) {
		case GuUriMatcher.KEYWORD_INCOMING_COLLECTION: {
			cv.put(Gu.KeywordsModel.Column.timestap, System.currentTimeMillis());
			long id = getDataBase().insert(Gu.KeywordsModel.NAME, "", cv);
			result = Uri.withAppendedPath(uri, "" + id);
			getContext().getContentResolver().notifyChange(result, null);
			break;
		}
		case GuUriMatcher.SEARCH_RESULT_INCOMING_COLLECTION: {
			long id = getDataBase().insert(Gu.SearchResultsModel.NAME, "", cv);
			result = Uri.withAppendedPath(uri, "" + id);
			getContext().getContentResolver().notifyChange(result, null);
			break;
		}
		case GuUriMatcher.ANALITYC_INCOMING_COLLECTION: {
			long id = getDataBase().insert(Gu.AnalitycModel.NAME, "", cv);
			result = Uri.withAppendedPath(uri, "" + id);
			getContext().getContentResolver().notifyChange(result, null);
			break;
		}
		
		default: {
			Log.e("Problem with query, not Implemented for : " + uri);
			throw new RuntimeException(
					"Problem with query, not Implemented for : " + uri);
		}
		}
		return result;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor;
		switch (matcher.match(uri)) {
		case GuUriMatcher.KEYWORD_INCOMING_COLLECTION: {
			cursor = getDataBase().query(Gu.KeywordsModel.NAME, projection,
					selection, selectionArgs, null, null, sortOrder, Gu.KEYWORDS_PAGE_LIMIT);
			break;
		}
		case GuUriMatcher.KEYWORD_INCOMING_ITEM: {
			cursor = getDataBase().query(Gu.KeywordsModel.NAME, projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		}
		case GuUriMatcher.SEARCH_RESULT_INCOMING_COLLECTION: {
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			qb.setDistinct(true);
			qb.setTables(Gu.SearchResultsModel.NAME + " left join "
							+ Gu.AnalitycModel.NAME + " on "
							+ Gu.SearchResultsModel.NAME + "."
							+ Gu.SearchResultsModel.Column.url + " = "
							+ Gu.AnalitycModel.NAME + "."
							+ Gu.AnalitycModel.Column.url);

			qb.setProjectionMap(searchResultProjectionMap);
			cursor = qb.query(getDataBase(), projection, selection,
					selectionArgs, null, null, Gu.SearchResultsModel.NAME + "." + Gu.SearchResultsModel.Column.id);
			break;
		}
		case GuUriMatcher.SEARCH_RESULT_INCOMING_ITEM: {
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			qb.setDistinct(true);
			qb.setTables(Gu.SearchResultsModel.NAME + " left join "
							+ Gu.AnalitycModel.NAME + " on "
							+ Gu.SearchResultsModel.NAME + "."
							+ Gu.SearchResultsModel.Column.url + " = "
							+ Gu.AnalitycModel.NAME + "."
							+ Gu.AnalitycModel.Column.url);

			qb.setProjectionMap(searchResultProjectionMap);
			cursor = qb.query(getDataBase(), projection, selection,
					selectionArgs, null, null, Gu.SearchResultsModel.NAME + "." + Gu.SearchResultsModel.Column.id);
			break;
		}
		case GuUriMatcher.ANALITYC_INCOMING_COLLECTION: {
			cursor = getDataBase().query(Gu.AnalitycModel.NAME, projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		}
		case GuUriMatcher.FEEDBACK_INCOMING_COLLECTION:{
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			qb.setDistinct(true);
			qb.setTables(Gu.SearchResultsModel.NAME + " left join "
							+ Gu.AnalitycModel.NAME + " on "
							+ Gu.SearchResultsModel.NAME + "."
							+ Gu.SearchResultsModel.Column.url + " = "
							+ Gu.AnalitycModel.NAME + "."
							+ Gu.AnalitycModel.Column.url);

			qb.setProjectionMap(feedbacksProjectionMap);
			cursor = qb.query(getDataBase(), projection, selection,
					selectionArgs, null, null,  Gu.SearchResultsModel.Column.title);
			break;
		}
		
		default: {
			Log.e("Problem with query, not Implemented for : " + uri);
			throw new RuntimeException(
					"Problem with query, not Implemented for : " + uri);
		}
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int rowUpdated = 0;
		switch (matcher.match(uri)) {
		case GuUriMatcher.SEARCH_RESULT_INCOMING_COLLECTION: {
			rowUpdated = getDataBase().update(Gu.SearchResultsModel.NAME,
					values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);

			break;
		}
		case GuUriMatcher.ANALITYC_INCOMING_COLLECTION: {
			rowUpdated = getDataBase().update(Gu.AnalitycModel.NAME,
					values, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);

			break;
		}
		default:
			Log.e("Problem with query, not Implemented for : " + uri);
			throw new RuntimeException(
					"Problem with query, not Implemented for : " + uri);
		}
		return rowUpdated;
	}

}
