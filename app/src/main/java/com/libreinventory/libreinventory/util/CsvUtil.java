package com.libreinventory.libreinventory.util;

/**
 * Created by jm on 31/05/2017.
 */


import android.content.Context;
import android.util.Log;

import com.libreinventory.libreinventory.model.Article;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CsvUtil {

    static public List<Article> parseCsvArticles(String res) {
        Log.w("csv", "Article parse start" + res);
        List<Article> articles = new ArrayList<Article>();

        if (res.isEmpty()) {
            return articles;
        }


        File file = new File(res);
        try {

            CSVReader reader = new CSVReader(new FileReader(file), ',', '\"');
            String[] nextLine;

            boolean firstLine = true;
            while ((nextLine = reader.readNext()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Article a = new Article();
                a.setId(Integer.valueOf(nextLine[0]));
                a.setArticle(nextLine[1]);
                a.setBarCode(nextLine[2]);

                articles.add(a);
            }
            //return data;

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