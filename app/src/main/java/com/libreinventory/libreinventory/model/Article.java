package com.libreinventory.libreinventory.model;

/**
 * Created by jm on 31/05/2017.
 */

public class Article {

    private int mId;
    private String mBarCode;
    private String mArticle;

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

    public String toString() {

        String sep = "/";
        return mId + sep + mArticle;
    }
}