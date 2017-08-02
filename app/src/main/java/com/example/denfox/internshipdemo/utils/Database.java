package com.example.denfox.internshipdemo.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.denfox.internshipdemo.models.GitRepoItem;

import java.util.List;

public class Database {

    private final Context ctx;

    private DBHelper dbHelper;

    private SQLiteDatabase mDB;

    public Database(Context ctx) {
        this.ctx = ctx;
    }

    public void open() {
        dbHelper = new DBHelper(ctx, Consts.DB_NAME, null, Consts.DB_VERSION);
        mDB = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor getAllData() {
        return mDB.query(Consts.DB_TABLE_NAME, null, null, null, null, null, Consts.DB_COL_ID_PRIMARY + " DESC");
    }

    public void clearData() {
        mDB.delete(Consts.DB_TABLE_NAME, null, null);
    }

    private void addRec(GitRepoItem item) {
        ContentValues cv = new ContentValues();
        cv.put(Consts.DB_COL_DESCRIPTION, item.getDescription());
        cv.put(Consts.DB_COL_ID, item.getRepoId());
        cv.put(Consts.DB_COL_NAME, item.getRepoName());
        cv.put(Consts.DB_COL_URL, item.getWebUrl().toString());

        mDB.insert(Consts.DB_TABLE_NAME, null, cv);
    }

    public void addApiData(List<GitRepoItem> items) {
        if(items.size() != 0) {
            for(int i = items.size()-1; i>=0; i--) {
                addRec(items.get(i));
            }
        }
    }

    /**
     * Subclass of {@link android.database.sqlite.SQLiteOpenHelper} which provides custom database helper.
     */
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Consts.DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Consts.DB_DELETE_ENTRIES);
            onCreate(db);
        }
    }

}
