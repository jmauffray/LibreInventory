package com.libreinventory.libreinventory.config;

import android.util.Pair;

import com.libreinventory.libreinventory.model.Article;

import java.util.ArrayList;
import java.util.List;

public class Config {

    static public List<Article> articles = new ArrayList<Article>();
    static public List<Pair<Article, String>> articlesCache = new ArrayList<Pair<Article, String>>();

}