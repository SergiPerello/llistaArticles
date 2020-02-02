package com.sergames.llistaarticles;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ArticlesAdapter extends android.widget.SimpleCursorAdapter {
    private MainActivity mainActivity;

    ArticlesAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mainActivity = (MainActivity) context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        Cursor linia = (Cursor) getItem(position);
        final int idPos = linia.getInt(linia.getColumnIndexOrThrow(ArticlesDataSource.ART_ID));
        int estoc = linia.getInt(linia.getColumnIndexOrThrow(ArticlesDataSource.ART_ESTOC));
        if (estoc <= 0) view.setBackgroundColor(Color.parseColor("#FF5533"));
        else view.setBackgroundColor(Color.parseColor("#FFFFFF"));

        ImageView addStock = view.findViewById(R.id.addStock);
        addStock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivity.showAlertDialogButtonClicked("Afegir stock", idPos, true);
            }
        });

        ImageView ivStockQuit = view.findViewById(R.id.removeStock);
        ivStockQuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivity.showAlertDialogButtonClicked("Treure stock", idPos, false);
            }
        });


        return view;
    }
}
