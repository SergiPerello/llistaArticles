package com.sergames.llistaarticles;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleMoviments extends AppCompatActivity {
    private static String[] from = new String[]{ArticlesDataSource.MOV_DIA, ArticlesDataSource.MOV_QUANTITAT, ArticlesDataSource.MOV_TIPUS};
    private static int[] to = new int[]{R.id.LblDia, R.id.LblQuantitat, R.id.LblTipus};
    private long idPos;
    private ArticleMovimentsAdapter artMovAdapter;
    private ArticlesDataSource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = new ArticlesDataSource(this);
        setContentView(R.layout.activity_article_moviments);
        idPos = this.getIntent().getExtras().getInt("idPos");
        loadArtMov();
    }

    private void loadArtMov() {
        Cursor cursor = bd.moviment(idPos);
        //Log.e("@HOLA", DatabaseUtils.dumpCursorToString(cursor));
        artMovAdapter = new ArticleMovimentsAdapter(this, R.layout.listitem_moviment, cursor, from, to, 1);
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(artMovAdapter);
    }
}
