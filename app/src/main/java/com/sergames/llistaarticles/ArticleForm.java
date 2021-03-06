package com.sergames.llistaarticles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ArticleForm extends Activity {
    private long idArticle;
    private ArticlesDataSource bd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_form);
        bd = new ArticlesDataSource(this);

        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept();
            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArticle();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        idArticle = this.getIntent().getExtras().getLong("id");

        if (idArticle != -1) loadData();//ThisUpdatesArticles
        else btnDelete.setVisibility(View.GONE);
    }

    private void accept() {
        TextView tv;
        tv = findViewById(R.id.edtCodiArticle);
        String codiArticle = tv.getText().toString();
        if (codiArticle.trim().equals("")) {
            Toast.makeText(this, "Codi article esta buit!", Toast.LENGTH_SHORT).show();
            return;
        }
        tv = findViewById(R.id.edtDescripcio);
        String descripcio = tv.getText().toString();
        if (descripcio.trim().equals("")) {
            Toast.makeText(this, "Descripció esta buit!", Toast.LENGTH_SHORT).show();
            return;
        }
        tv = findViewById(R.id.edtPvp);
        float pvp;
        try {
            pvp = Float.valueOf(tv.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Pvp ha de ser un numero", Toast.LENGTH_SHORT).show();
            return;
        }
        tv = findViewById(R.id.edtEstoc);
        float estoc;
        try {
            estoc = Float.valueOf(tv.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Estoc ha de ser un numero", Toast.LENGTH_SHORT).show();
            return;
        }
        if (idArticle == -1) {
            tv = findViewById(R.id.edtCodiArticle);
            Cursor c = bd.checkCodiArticle(tv.getText().toString());
            if (c.getCount() > 0) {
                Toast.makeText(this, "Codi ja existent", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (estoc < 0){
                Toast.makeText(this, "Estoc ha de ser un numero superior a 0", Toast.LENGTH_SHORT).show();
                return;
            }
            idArticle = bd.addArticle(codiArticle, descripcio, pvp, estoc);
        }
        else bd.updateArticle(idArticle, codiArticle, descripcio, pvp, estoc);

        Intent mIntent = new Intent();
        mIntent.putExtra("id", idArticle);
        setResult(RESULT_OK, mIntent);

        finish();
    }

    private void cancel() {
        Intent mIntent = new Intent();
        mIntent.putExtra("id", idArticle);
        setResult(RESULT_CANCELED, mIntent);
        finish();
    }

    private void loadData() {
        Cursor datos = bd.article(idArticle);
        datos.moveToFirst();

        TextView tv;
        tv = findViewById(R.id.edtCodiArticle);
        tv.setText(datos.getString(datos.getColumnIndex(ArticlesDataSource.ART_CODI)));
        tv.setEnabled(false);

        tv = findViewById(R.id.edtDescripcio);
        tv.setText(datos.getString(datos.getColumnIndex(ArticlesDataSource.ART_DESCRIPCIO)));

        tv = findViewById(R.id.edtPvp);
        tv.setText(datos.getString(datos.getColumnIndex(ArticlesDataSource.ART_PVP)));

        tv = findViewById(R.id.edtEstoc);
        tv.setText(datos.getString(datos.getColumnIndex(ArticlesDataSource.ART_ESTOC)));
    }

    private void deleteArticle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desitja eliminar l'article?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.deleteArticle(idArticle);
                Intent mIntent = new Intent();
                mIntent.putExtra("id", -1);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
