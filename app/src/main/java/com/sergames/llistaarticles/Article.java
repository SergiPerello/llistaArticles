package com.sergames.llistaarticles;

public class Article {
    private String codiArticle;
    private String descripcio;
    private float pvp;
    private float estoc;

    public Article(String codiArticle, String descripcio, float pvp, float estoc) {
        this.codiArticle = codiArticle;
        this.descripcio = descripcio;
        this.pvp = pvp;
        this.estoc = estoc;
    }

    public String getCodiArticle() {
        return codiArticle;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public float getPvp() {
        return pvp;
    }

    public float getEstoc() {
        return estoc;
    }
}