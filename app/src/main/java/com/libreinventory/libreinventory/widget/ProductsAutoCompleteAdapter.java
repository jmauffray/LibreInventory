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

package com.libreinventory.libreinventory.widget;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ProductsAutoCompleteAdapter extends ArrayAdapter<Article> implements Filterable {
    private List<Article> resultList;
    private List<Pair<Article, String>> cache;

    public ProductsAutoCompleteAdapter(Context context, int textViewResourceId, List<Article> resultList) {
        super(context, textViewResourceId, resultList);
        this.resultList = resultList;
        cache = Config.articlesCache;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Article getItem(int index) {
        if (index < resultList.size()) {
            return resultList.get(index);
        } else {
            return new Article();
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                long startTime = System.currentTimeMillis();
                Log.w("start filter", "");

                if (constraint != null &&
                        !constraint.toString().isEmpty()) {
                    String s = constraint.toString();
                    String[] tokens = s.split(" ");

                    //tokens to lower case
                    String[] tokensLower = new String[tokens.length];
                    for (int i = 0; i < tokens.length; i++) {
                        tokensLower[i] = tokens[i].toLowerCase();
                    }

                    // Retrieve the autocomplete results.
                    boolean match = true;
                    List<Article> resultList1 = new ArrayList<Article>();
                    for (Pair<Article, String> cacheItem : cache) {
                        match = true;
                        for (String tokenLower : tokensLower) {
                            if (!cacheItem.second.contains(tokenLower)) {
                                match = false;
                                break;
                            } else {
                            }
                        }
                        if (match) {
                            resultList1.add(cacheItem.first);
                        }
                    }
                    // Assign the data to the FilterResults
                    filterResults.values = resultList1;
                    filterResults.count = resultList1.size();
                } else {
                    synchronized (resultList) {
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                }

                Log.w("duration", "That took " + (System.currentTimeMillis() - startTime) +
                        " milliseconds");

                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                resultList = (List<Article>) results.values;
                if (results != null && results.count > 0) {

                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

}