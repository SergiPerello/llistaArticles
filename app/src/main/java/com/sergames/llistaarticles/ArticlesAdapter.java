package com.sergames.llistaarticles;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class ArticlesAdapter extends android.widget.SimpleCursorAdapter {
    private MainActivity oTodoListIcon;

    ArticlesAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        oTodoListIcon = (MainActivity) context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Cursor linia = (Cursor) getItem(position);
        int estoc = linia.getInt(linia.getColumnIndexOrThrow(ArticlesDataSource.ESTOC));
        if (estoc <= 0) view.setBackgroundColor(Color.parseColor("#FF5533"));
        else view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        return view;
    }
}
