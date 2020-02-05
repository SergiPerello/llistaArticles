package com.sergames.llistaarticles;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ArticleMovimentsAdapter extends android.widget.SimpleCursorAdapter{
    public ArticleMovimentsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        TextView tv = view.findViewById(R.id.LblDia);
        tv.setText(DateFormatter.format((String) tv.getText()));
        return view;
    }
}
