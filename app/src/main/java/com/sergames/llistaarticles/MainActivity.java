package com.sergames.llistaarticles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static int ACTIVITY_TASK_ADD = 1;
    private static int ACTIVITY_TASK_UPDATE = 2;

    private static String[] from = new String[]{ArticlesDataSource.CODIARTICLE, ArticlesDataSource.DESCRIPCIO, ArticlesDataSource.PVP, ArticlesDataSource.ESTOC};
    private static int[] to = new int[]{R.id.LblCodiArticle, R.id.LblDescripcio, R.id.LblPvp, R.id.LblEstoc};
    private ArticlesDataSource bd;
    private long idActual;
    private int posActual;
    private ArticlesAdapter scArticles;
    private filterKind filterActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Cursor cursorTasks = bd.articles();
        scArticles = new ArticlesAdapter(this, R.layout.listitem_article, cursorTasks, from, to, 1);
        filterActual = filterKind.FILTER_ALL;
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(scArticles);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        updateArticle(id);
                    }
                }
        );
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
            case R.id.mnuTot:
                filterTot();
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
        Cursor cursor = null;
        switch (filterActual){
            case FILTER_ALL:
                cursor = bd.articles();
                break;
            case FILTER_STOCK:
                cursor = bd.articlesStock();
                break;
            case FILTER_NOSTOCK:
                cursor = bd.articlesNoStock();
                break;
        }
        scArticles.changeCursor(cursor);
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

    public void deleteArticle(final int _id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Eliminar article?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.deleteArticle(_id);
                refreshArticles();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void filterTot() {
        Cursor cursorTasks = bd.articles();
        filterActual = filterKind.FILTER_ALL;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Toast.makeText(this, "Tots els articles", Toast.LENGTH_SHORT).show();
    }

    private void filterStock() {
        Cursor cursorTasks = bd.articlesStock();
        filterActual = filterKind.FILTER_STOCK;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Toast.makeText(this, "Articles en stock", Toast.LENGTH_SHORT).show();
    }

    private void filterNoStock() {
        Cursor cursorTasks = bd.articlesNoStock();
        filterActual = filterKind.FILTER_NOSTOCK;
        scArticles.changeCursor(cursorTasks);
        scArticles.notifyDataSetChanged();
        ListView lv = findViewById(R.id.list);
        lv.setSelection(0);
        Toast.makeText(this, "Articles en no stock", Toast.LENGTH_SHORT).show();
    }
}
