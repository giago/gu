package org.chickymate.gu.provider;

import org.chickymate.gu.utils.Gu;

import android.content.UriMatcher;
import android.net.Uri;


public class GuUriMatcher extends UriMatcher {

	public static final String SEARCH_RESULT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gu.result";
	public static final String SEARCH_RESULT_COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.gu.result";
	public static final int SEARCH_RESULT_INCOMING_ITEM = 10;
	public static final int SEARCH_RESULT_INCOMING_COLLECTION = 20;
	
	public static final String KEYWORD_ITEM_TYPE = "vnd.android.cursor.item/vnd.gu.search";
	public static final String KEYWORD_COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.gu.search";
	public static final int KEYWORD_INCOMING_ITEM = 30;
	public static final int KEYWORD_INCOMING_COLLECTION = 40;
	
	public static final String ANALITYC_ITEM_TYPE = "vnd.android.cursor.item/vnd.gu.analityc";
	public static final String ANALITYC_COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.gu.analytic";
	public static final int ANALITYC_INCOMING_ITEM = 50;
	public static final int ANALITYC_INCOMING_COLLECTION = 60;
	
	public static final String FEEDBACK_COLLECTION_TYPE = "vnd.android.cursor.item/vnd.gu.feedback";
	public static final int FEEDBACK_INCOMING_COLLECTION = 70;
	
	public GuUriMatcher(int code) {
        super(code);
        setUp();
    }

	public GuUriMatcher() {
		super(UriMatcher.NO_MATCH);
        setUp();
    }

    public void setUp() {
        add(Gu.SearchResultsModel.NAME, SEARCH_RESULT_INCOMING_COLLECTION);
        add(Gu.SearchResultsModel.NAME + "/#", SEARCH_RESULT_INCOMING_ITEM);
        add(Gu.KeywordsModel.NAME, KEYWORD_INCOMING_COLLECTION);
        add(Gu.KeywordsModel.NAME + "/#", KEYWORD_INCOMING_ITEM);
        add(Gu.AnalitycModel.NAME, ANALITYC_INCOMING_COLLECTION);
        add(Gu.AnalitycModel.NAME + "/#", ANALITYC_INCOMING_ITEM);
        add(Gu.Feedback.NAME, FEEDBACK_INCOMING_COLLECTION);
    }

    public void add(String path, int code) {
        super.addURI(Gu.AUTHORITY, path, code);
    }

    public static final String[] getIdSelectionArgumentsFromUri(Uri uri) {
        return new String[] {
            uri.getPathSegments().get(1)
        };
    }
}
