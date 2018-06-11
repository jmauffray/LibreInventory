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

package com.libreinventory.libreinventory.util;

import android.content.Context;
import android.util.Log;

import com.libreinventory.libreinventory.model.Article;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CsvUtil {

    final static int CSV_COLUMN_REFERENCE = 0;
    final static int CSV_COLUMN_DESCRIPTION = 1;
    final static int CSV_COLUMN_BARCODE = 6;

    static public List<Article> parseCsvArticles(InputStream is) {

        List<Article> articles = new ArrayList<Article>();

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(is), ',', '\"');
            String[] nextLine;

            boolean firstLine = true;
            while ((nextLine = reader.readNext()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Article a = new Article();
                a.setId(Integer.valueOf(nextLine[CSV_COLUMN_REFERENCE]));
                a.setArticle(nextLine[CSV_COLUMN_DESCRIPTION]);
                a.setBarCode(nextLine[CSV_COLUMN_BARCODE]);

                articles.add(a);
            }
        } catch (Exception e) {
            Log.e("Error", "Error for importing file");
            Log.e("Error", e.toString());
        }

        Log.w("csv", "Article parse ended");

        return articles;
    }

    static public void saveCsv(String res, String filename, Context mContext) {
        Log.e("csv", "save start");
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(filename));
            outputStream.write(res.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("csv", "save ended");
    }

    static public String getLocalCsv(String filename, Context mContext) {
        Log.e("csv", "get local start");
        String ret = "";
        try {
            InputStream inputStream = mContext.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Log.e("csv", "get local ended");
        return ret;
    }

}