package com.sergames.llistaarticles;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleMoviments extends AppCompatActivity {
    private long idPos;
    private ArticleMovimentsAdapter artMovAdapter;
    private ArticlesDataSource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_moviments);

        idPos = this.getIntent().getExtras().getLong("idPos");
    }
    private void loadArtMov() {
        Cursor cursorTasks = bd.moviment(idPos);
        artMovAdapter = new ArticlesAdapter(this, R.layout.listitem_moviment, cursorTasks, from, to, 1);
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(artMovAdapter);
    }
}
