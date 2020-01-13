package com.sergames.llistaarticles;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

    private static int ACTIVITY_TASK_ADD = 1;
    private static int ACTIVITY_TASK_UPDATE = 2;

    private static String[] from = new String[]{ArticlesDataSource.CODIARTICLE, ArticlesDataSource.DESCRIPCIO, ArticlesDataSource.PVP, ArticlesDataSource.ESTOC};
    private static int[] to = new int[]{R.id.LblCodiArticle, R.id.LblDescripcio, R.id.LblPvp, R.id.LblEstoc};
    private ArticlesDataSource bd;
    private long idActual;
    private int posActual;
    private SimpleCursorAdapter scArticles;
    private filterKind filterActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.btnAdd);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        setTitle("Articles");
        bd = new ArticlesDataSource(this);
        loadArticles();
    }

    private void loadArticles() {
        Cursor cursorTasks = bd.articles();
        scArticles = new SimpleCursorAdapter(this, R.layout.listitem_article, cursorTasks, from, to, 1);
        setListAdapter(scArticles);
    }

    private void refreshArticles() {

        // Demanem totes les tasques
        Cursor cursorTasks = bd.articles();

        // Now create a simple cursor adapter and set it to display
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
    }

    private void addTask() {
        // Cridem a l'activity del detall de la tasca enviant com a id -1
        Bundle bundle = new Bundle();
        bundle.putLong("id", -1);

        idActual = -1;

        Intent i = new Intent(this, ArticleForm.class);
        i.putExtras(bundle);
        startActivityForResult(i, ACTIVITY_TASK_ADD);
    }

    private void updateTask(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        idActual = id;
        Intent i = new Intent(this, ArticleForm.class);
        i.putExtras(bundle);
        startActivityForResult(i, ACTIVITY_TASK_UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_TASK_ADD) {
            if (resultCode == RESULT_OK) refreshArticles();
        }
        if (requestCode == ACTIVITY_TASK_UPDATE) {
            if (resultCode == RESULT_OK) refreshArticles();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        updateTask(id);
    }
}
