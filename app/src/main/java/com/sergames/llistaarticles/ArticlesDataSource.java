package com.sergames.llistaarticles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArticlesDataSource {
    private static final String table_ARTICLES = "articles";
    private static final String ART_ID = "_id";
    private static final String ART_CODI = "codi";
    private static final String ART_DESCRIPCIO = "descripcio";
    private static final String ART_PVP = "pvp";
    private static final String ART_ESTOC = "estoc";

    private static final String table_MOVIMENT = "moviments";
    private static final String MOV_ID = "_id";
    private static final String MOV_CODI = "codi";
    private static final String MOV_DIA = "dia";
    private static final String MOV_QUANTITAT = "quantitat";
    private static final String MOV_TIPUS = "tipus";
    private static final String MOV_ART_ID = "art_id";

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

    /* TAULA ARTICLES */
    public Cursor articles() {
        return dbR.query(table_ARTICLES, new String[]{ART_ID, ART_CODI, ART_DESCRIPCIO, ART_PVP, ART_ESTOC},
                null, null, null, null, ART_ID);
    }

    public Cursor article(long id) {
        return dbR.query(table_ARTICLES, new String[]{ART_ID, ART_CODI, ART_DESCRIPCIO, ART_PVP, ART_ESTOC},
                ART_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public long addArticle(String codiArticle, String descripcio, float pvp, float estoc) {
        ContentValues values = new ContentValues();
        values.put(ART_CODI, codiArticle);
        values.put(ART_DESCRIPCIO, descripcio);
        values.put(ART_PVP, pvp);
        values.put(ART_ESTOC, estoc);
        return dbW.insert(table_ARTICLES, null, values);
    }

    public void updateArticle(long id, String codiArticle, String descripcio, float pvp, float estoc) {
        ContentValues values = new ContentValues();
        values.put(ART_CODI, codiArticle);
        values.put(ART_DESCRIPCIO, descripcio);
        values.put(ART_PVP, pvp);
        values.put(ART_ESTOC, estoc);
        dbW.update(table_ARTICLES, values, ART_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteArticle(long id) {
        dbW.delete(table_ARTICLES, ART_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor articlesStock() {
        return dbR.query(table_ARTICLES, new String[]{ART_ID, ART_CODI, ART_DESCRIPCIO, ART_PVP, ART_ESTOC},
                ART_ESTOC + ">?", new String[]{String.valueOf(0)}, null, null, ART_CODI);
    }

    public Cursor articlesNoStock() {
        return dbR.query(table_ARTICLES, new String[]{ART_ID, ART_CODI, ART_DESCRIPCIO, ART_PVP, ART_ESTOC},
                ART_ESTOC + "<=?", new String[]{String.valueOf(0)}, null, null, ART_CODI);
    }

    public Cursor checkCodiArticle(String codi) {
        return dbR.query(table_ARTICLES, new String[]{ART_ID, ART_CODI, ART_DESCRIPCIO, ART_PVP, ART_ESTOC},
                ART_CODI + "=?", new String[]{codi}, null, null, ART_CODI);
    }

    /* TAULA MOVIMENTS */
    public Cursor moviments() {
        return dbR.query(table_MOVIMENT, new String[]{MOV_ID, MOV_CODI, MOV_DIA, MOV_QUANTITAT, MOV_TIPUS, MOV_ART_ID},
                null, null, null, null, MOV_ID);
    }

    public Cursor moviment(long id) {
        return dbR.query(table_MOVIMENT, new String[]{MOV_ID, MOV_CODI, MOV_DIA, MOV_QUANTITAT, MOV_TIPUS, MOV_ART_ID},
                MOV_ART_ID + "=?", new String[]{String.valueOf(id)}, null, null, MOV_ID);
    }

    public long addMoviment(String codi, String dia, int quantitat, String tipus, int mov_art_id) {
        ContentValues values = new ContentValues();
        values.put(MOV_CODI, codi);
        values.put(MOV_DIA, dia);
        values.put(MOV_QUANTITAT, quantitat);
        values.put(MOV_TIPUS, tipus);
        values.put(MOV_ART_ID, mov_art_id);
        return dbW.insert(table_MOVIMENT,null,values);
    }

}
