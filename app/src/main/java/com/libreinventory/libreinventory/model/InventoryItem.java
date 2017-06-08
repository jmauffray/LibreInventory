package com.libreinventory.libreinventory.model;

/**
 * Created by jm on 31/05/2017.
 */

public class InventoryItem {

    //public int mId = 0;
    public int mArticleId = 0;
    public int mQuantite = 0;
    private String mLocalisation;

    public int getArticleId() {
        return mArticleId;
    }

    public void setArticleId(int id) {
        this.mArticleId = id;
    }

    public int getQuantite() {
        return mQuantite;
    }

    public void setQuantite(int quantite) {
        this.mQuantite = quantite;
    }

    public String getLocalisation() {
        return mLocalisation;
    }

    public void setLocalisation(String mLocalisation) {
        this.mLocalisation = mLocalisation;
    }

    public String toString() {
        return "Id:" + mArticleId + " / " + " Q:" + mQuantite + " / Loc:" + mLocalisation;
    }

}