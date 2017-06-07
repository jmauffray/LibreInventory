package com.libreinventory.libreinventory.model;

/**
 * Created by jm on 31/05/2017.
 */

public class Article {

    private int mId;
    private String mBarCode;
    private String mArticle;


    /*
    private String mVariete;
    private String mTaille;
    private String mPrixTtcPart;
    private String mOrigine;
    */

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getBarCode() {
        return mBarCode;
    }

    public void setBarCode(String mBarCode) {
        this.mBarCode = mBarCode;
    }

    public String getArticle() {
        return mArticle;
    }

    public void setArticle(String article) {
        this.mArticle = article;
    }

    /*
    public String getVariete() {
        return mVariete;
    }

    public void setVariete(String variete) {
        this.mVariete = variete;
    }

    public String getTaille() {
        return mTaille;
    }

    public void setTaille(String taille) {
        this.mTaille = taille;
    }

    public String getPrixTtcPart() {
        return mPrixTtcPart;
    }

    public void setPrixTtcPart(String prixTtcPart) {
        this.mPrixTtcPart = prixTtcPart;
    }

    public String getOrigine() {
        return mOrigine;
    }

    public void setOrigine(String origine) {
        this.mOrigine = origine;
    }
*/
    public String toString() {
        String sep = "/";
        return mId + sep + mArticle  /* + sep + mVariete + sep + mTaille + sep + mOrigine*/;
    }
}