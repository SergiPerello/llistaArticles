package com.sergames.llistaarticles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArticlesHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 2;

    // database name
    private static final String database_NAME = "articlesDataBase";

    public ArticlesHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    private String CREATE_ARTICLES =
            "CREATE TABLE articles (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "codi TEXT," +
                    "descripcio TEXT," +
                    "pvp FLOAT," +
                    "estoc FLOAT)";

    private String CREATE_MOVIMENTS =
            "CREATE TABLE moviments (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "codiArticle TEXT," +
                    "dia TEXT," +
                    "quantitat INTEGER," +
                    "tipus TEXT," +
                    "FOREIGN KEY(codiArticle) REFERENCES articles(_id))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLES);
        db.execSQL(CREATE_MOVIMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<2) db.execSQL(CREATE_MOVIMENTS);
    }
}
