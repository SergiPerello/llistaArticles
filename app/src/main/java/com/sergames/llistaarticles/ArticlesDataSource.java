package com.sergames.llistaarticles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArticlesDataSource {
    public static final String table_ARTICLES = "articles";
    public static final String ID = "_id";
    public static final String CODIARTICLE = "codiArticle";
    public static final String DESCRIPCIO = "descripcio";
    public static final String PVP = "pvp";
    public static final String ESTOC = "estoc";

    private ArticlesHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    public ArticlesDataSource(Context ctx) {
        dbHelper = new ArticlesHelper(ctx);
        open();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    protected void finalize() {
        dbW.close();
        dbR.close();
    }

    public Cursor articles() {
        return dbR.query(table_ARTICLES, new String[]{ID, CODIARTICLE, DESCRIPCIO, PVP, ESTOC},
                null, null, null, null, ID);
    }

    public Cursor article(long id) {
        return dbR.query(table_ARTICLES, new String[]{ID, CODIARTICLE, DESCRIPCIO, PVP, ESTOC},
                ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

    }

    public long addArticle(String codiArticle, String descripcio, float pvp, float estoc) {
        ContentValues values = new ContentValues();
        values.put(CODIARTICLE, codiArticle);
        values.put(DESCRIPCIO, descripcio);
        values.put(PVP, pvp);
        values.put(ESTOC, estoc);

        return dbW.insert(table_ARTICLES, null, values);
    }

    public void updateArticle(long id, String codiArticle, String descripcio, float pvp, float estoc) {
        ContentValues values = new ContentValues();
        values.put(CODIARTICLE, codiArticle);
        values.put(DESCRIPCIO, descripcio);
        values.put(PVP, pvp);
        values.put(ESTOC, estoc);

        dbW.update(table_ARTICLES, values, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteArticle(long id) {
        dbW.delete(table_ARTICLES, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor articlesStock() {
        return dbR.query(table_ARTICLES, new String[]{ID, CODIARTICLE, DESCRIPCIO, PVP, ESTOC},
                ESTOC + ">?", new String[]{String.valueOf(0)}, null, null, CODIARTICLE);
    }

    public Cursor articlesNoStock() {
        return dbR.query(table_ARTICLES, new String[]{ID, CODIARTICLE, DESCRIPCIO, PVP, ESTOC},
                ESTOC + "<=?", new String[]{String.valueOf(0)}, null, null, CODIARTICLE);
    }

    public Cursor checkCodiArticle(String codi) {
        return dbR.query(table_ARTICLES, new String[]{ID, CODIARTICLE, DESCRIPCIO, PVP, ESTOC},
                CODIARTICLE + "=?", new String[]{codi}, null, null, CODIARTICLE);
    }

}
