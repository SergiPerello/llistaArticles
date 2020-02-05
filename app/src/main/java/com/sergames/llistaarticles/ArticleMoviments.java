package com.sergames.llistaarticles;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class ArticleMoviments extends AppCompatActivity {
    private static String[] from = new String[]{ArticlesDataSource.MOV_DIA, ArticlesDataSource.MOV_QUANTITAT, ArticlesDataSource.MOV_TIPUS};
    private static int[] to = new int[]{R.id.LblDia, R.id.LblQuantitat, R.id.LblTipus};
    private long idPos;
    private ArticleMovimentsAdapter artMovAdapter;
    private ArticlesDataSource bd;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = new ArticlesDataSource(this);
        setContentView(R.layout.activity_article_moviments);
        idPos = this.getIntent().getExtras().getInt("idPos");
        loadArtMov();
        final TextView tvInitialDate = findViewById(R.id.tvInitialDate);
        tvInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(tvInitialDate);
            }
        });
        final TextView tvFinalDate = findViewById(R.id.tvFinalDate);
        tvFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(tvFinalDate);
            }
        });
    }

    private void loadArtMov() {
        Cursor cursor = bd.moviment(idPos);
        artMovAdapter = new ArticleMovimentsAdapter(this, R.layout.listitem_moviment, cursor, from, to, 1);
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(artMovAdapter);
    }

    public void showAlertDialog(final TextView editText) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String sDay = String.valueOf(day);
                String sMonth = String.valueOf(month);
                if (sDay.length() != 2) sDay = "0" + sDay;
                if (sMonth.length() != 2) sMonth = "0" + sMonth;
                String date = sDay + "/" + sMonth + "/" + year;
                editText.setText(date);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(
                getApplicationContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}