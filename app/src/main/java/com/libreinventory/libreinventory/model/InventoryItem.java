/*
 * Libre Inventory is a simple inventory android application
 *
 * Copyright (c) 2017 "Jean-Marie Auffray,"
 *
 * This file is part of Libre Inventory.
 *
 * Libre Inventory is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.libreinventory.libreinventory.model;

public class InventoryItem {

    //public int mId = 0;
    public int mArticleId = 0;
    public int mQuantite = 0;
    public int mQuantiteVente = 0;
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

    public int getQuantiteVente() {
        return mQuantiteVente;
    }

    public void setQuantite(int quantite) {
        this.mQuantite = quantite;
    }

    public void setQuantiteVente(int quantite) {
        this.mQuantiteVente = quantite;
    }

    public String getLocalisation() {
        return mLocalisation;
    }

    public void setLocalisation(String mLocalisation) {
        this.mLocalisation = mLocalisation;
    }

    public String toString() {
        return "Id:" + mArticleId + " / " + " Q:" + mQuantite + " / " + " QV:" + mQuantiteVente +
                " / Loc:" + mLocalisation;
    }

}