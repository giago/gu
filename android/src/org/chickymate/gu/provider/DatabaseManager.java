package org.chickymate.gu.provider;

import java.util.ArrayList;
import java.util.List;

import org.chickymate.gu.utils.Gu;
import org.chickymate.gu.utils.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseManager extends SQLiteOpenHelper {

	private static final String PREFIX = "insert into " + Gu.KeywordsModel.NAME 
		+ "(" + Gu.KeywordsModel.Column.keywordTerm 
		+ ","  + Gu.KeywordsModel.Column.timestap + ") values(\"";
	private static final String SEPARATOR = "\",";
	private static final String END = ");";
	
    public DatabaseManager(Context context) {
        super(context, Gu.Database.NAME, null, Gu.Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        drop(db);
        onCreate(db);
    }

    private void create(SQLiteDatabase db) {
    	List<String> stms = new ArrayList<String>();
    	stms.add(Gu.KeywordsModel.CREATE_STM);
    	stms.add(Gu.SearchResultsModel.CREATE_STM);
    	stms.add(Gu.SearchResultsModel.CREATE_CLEAN_TRIGGER_STM);
    	stms.add(Gu.AnalitycModel.CREATE_STM);
    	long time = System.currentTimeMillis();
    	stms.add(getInsert("1 android", time));
    	stms.add(getInsert("2 is", time));
    	stms.add(getInsert("3 the", time));
    	stms.add(getInsert("4 best", time));
    	stms.add(getInsert("5 by", time));
    	stms.add(getInsert("6 chickymate", time));
        exec(db, stms);
    }
    
    private String getInsert(String name, long time) {
    	return PREFIX + name + SEPARATOR + (time - Gu.DATA_REFRESH_PERIOD -10) + END;
    }

    private void drop(SQLiteDatabase db) {
        List<String> stms = new ArrayList<String>();
        stms.add(Gu.KeywordsModel.DROP_STM);
        stms.add(Gu.SearchResultsModel.DROP_STM);
        stms.add(Gu.SearchResultsModel.DROP_CLEAN_TRIGGER_STM);
        stms.add(Gu.AnalitycModel.DROP_STM);
        exec(db, stms);
    }

    private static final void exec(SQLiteDatabase db, List<String> staments) {
        for (String stm : staments) {
            try {
                db.execSQL(stm);
            } catch (RuntimeException re) {
            	if(Log.errorLoggingEnabled()) {
            		Log.e("RuntimeException", re);
            	}
            }
        }
    }

}