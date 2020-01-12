package com.sergames.llistaarticles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArticlesHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;

    // database name
    private static final String database_NAME = "articlesDataBase";

    public ArticlesHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLES =
                "CREATE TABLE articles ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codiArticle TEXT," +
                        "descripcio TEXT," +
                        "pvp FLOAT," +
                        "estoc FLOAT)";

        db.execSQL(CREATE_ARTICLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // De moment no fem res

    }
}
