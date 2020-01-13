package com.sergames.llistaarticles;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.material.snackbar.Snackbar;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.btnAdd);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addArticle();
            }
        });

        setTitle("Articles");
        bd = new ArticlesDataSource(this);
        loadArticles();
    }

    private void loadArticles() {
        Cursor cursorArticles = null;
        switch (filterActual) {
            case FILTER_ALL:
                cursorArticles = bd.articles();
                break;
            case FILTER_STOCK:
                cursorArticles = bd.articlesStock();
                break;
            case FILTER_NOSTOCK:
                cursorArticles = bd.articlesNoStock();
                break;
        }
        scArticles.changeCursor(cursorArticles);
        scArticles.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                addArticle();
                return true;
            case R.id.mnuStock:
                filterStock();
                return true;
            case R.id.mnuNoStock:
                filterNoStock();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


    private void refreshArticles() {
        Cursor cursorTasks = bd.articles();
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
    }

    private void addArticle() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", -1);
        idActual = -1;
        Intent i = new Intent(this, ArticleForm.class);
        i.putExtras(bundle);
        startActivityForResult(i, ACTIVITY_TASK_ADD);
    }

    private void updateArticle(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        idActual = id;
        Intent i = new Intent(this, ArticleForm.class);
        i.putExtras(bundle);
        startActivityForResult(i, ACTIVITY_TASK_UPDATE);
    }

    private void filterTot() {
        Cursor cursorTasks = bd.articles();
        filterActual = filterKind.FILTER_ALL;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Snackbar.make(findViewById(android.R.id.content), "Tots els articles", Snackbar.LENGTH_LONG).show();
    }

    private void filterStock() {
        Cursor cursorTasks = bd.articlesStock();
        filterActual = filterKind.FILTER_STOCK;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Snackbar.make(findViewById(android.R.id.content), "Articles en stock", Snackbar.LENGTH_LONG).show();
    }

    private void filterNoStock() {
        Cursor cursorTasks = bd.articlesNoStock();
        filterActual = filterKind.FILTER_NOSTOCK;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Snackbar.make(findViewById(android.R.id.content), "Articles en no stock", Snackbar.LENGTH_LONG).show();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        updateArticle(id);
    }
}
