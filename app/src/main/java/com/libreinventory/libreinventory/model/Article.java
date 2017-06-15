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