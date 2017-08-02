package com.example.denfox.internshipdemo.utils;


public class Consts {

    public final static String PREFS_NAME = "com.example.denfox.internshipdemo.SETTINGS";
    public final static String PREFS_USERS_REPO = "PREFS_USERS_REPO";
    public final static String PREFS_DONT_CLEAR_LIST = "PREFS_DONT_CLEAR_LIST";

    public final static String PREFS_REPO_LIST = "PREFS_REPO_LIST";


    // Configuration of a CursorLoader
    public final static int LOADER_ID = 0;

    // Configuration of a database
    public final static String DB_NAME = "internship_app_db";
    public final static String DB_TABLE_NAME = "repos";
    public final static String DB_COL_ID_PRIMARY = "_id";
    public final static String DB_COL_ID = "repoId";
    public final static String DB_COL_DESCRIPTION = "repoDescription";
    public final static String DB_COL_NAME = "repoName";
    public final static String DB_COL_URL = "repoURL";
    public final static int DB_VERSION = 1;

    // SQL Query
    public static final String DB_CREATE =
            "create table " + DB_TABLE_NAME + "(" +
                    DB_COL_ID_PRIMARY + " integer primary key autoincrement, " +
                    DB_COL_ID + " integer, " +
                    DB_COL_DESCRIPTION + " text, " +
                    DB_COL_URL + " text," +
                    DB_COL_NAME + " text," +
                    " UNIQUE ( " + DB_COL_ID + " ) ON CONFLICT IGNORE" +
                    ");";

    public static final String DB_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DB_TABLE_NAME;


}
