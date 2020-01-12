package com.sergames.llistaarticles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArticlesAdapter extends ArrayAdapter<Article> {
    public ArticlesAdapter(Context context, Article[] datos) {
        super(context, R.layout.listitem_article, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View item = inflater.inflate(R.layout.listitem_article, null);

        Article article = getItem(position);

        TextView lblCodiArticle = item.findViewById(R.id.LblCodiArticle);
        lblCodiArticle.setText(article.getCodiArticle());

        TextView lblDescripcio = item.findViewById(R.id.LblDescripcio);
        lblDescripcio.setText(article.getDescripcio());

        TextView lblPvp = item.findViewById(R.id.LblPvp);
        lblPvp.setText(String.valueOf(article.getPvp()));

        TextView lblEstoc = item.findViewById(R.id.LblEstoc);
        lblEstoc.setText(String.valueOf(article.getEstoc()));

        return (item);
    }
}
