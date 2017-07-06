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

package com.libreinventory.libreinventory.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.libreinventory.libreinventory.R;
import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.db.InventoryItemDAO;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.model.InventoryItem;
import com.libreinventory.libreinventory.widget.ProductsAutoCompleteAdapter;

import java.sql.SQLException;


public class InventoryActivity extends Activity implements OnClickListener {

    private View mRootView;

    // UI references.
    private AutoCompleteTextView mProductText;
    private EditText mBarCode;
    private EditText mReferenceText;
    private EditText mQuantiteText;
    private EditText mQuantiteVenteText;
    private EditText mLocalisationText;
    private EditText mCommentext;
    private EditText mEtiquetteNbText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mRootView = getWindow().getDecorView();

        mProductText = (AutoCompleteTextView) mRootView.findViewById(R.id.autoCompleteTextProduct);
        mBarCode = (EditText) mRootView.findViewById(R.id.editTextBarCode);
        mReferenceText = (EditText) mRootView.findViewById(R.id.editTextRef);
        mQuantiteText = (EditText) mRootView.findViewById(R.id.editTextQuantite);
        mQuantiteVenteText = (EditText) mRootView.findViewById(R.id.editTextQuantiteVente);
        mLocalisationText = (EditText) mRootView.findViewById(R.id.editTextLoc);
        mCommentext = (EditText) mRootView.findViewById(R.id.editTextComment);
        mEtiquetteNbText = (EditText) mRootView.findViewById(R.id.editTextEtiquetteNb);

        //button listeners
        Button b = (Button) mRootView.findViewById(R.id.buttonOk);
        b.setOnClickListener(this);

        b = (Button) mRootView.findViewById(R.id.buttonCancel);
        b.setOnClickListener(this);

        mBarCode.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(Config.articles.isEmpty()) {
                    return;
                }
                int barCodeSize = Config.articles.get(0).getBarCode().length();

                if (s.toString().length() != barCodeSize) {
                    return;
                }
                for (Article a : Config.articles) {
                    if (a.getBarCode().equals(s.toString())) {
                        mReferenceText.setText(String.valueOf(a.getId()));
                        mProductText.setText("");
                        break;
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        //autocomplete
        AutoCompleteTextView t =
                (AutoCompleteTextView) mRootView.findViewById(
                        R.id.autoCompleteTextProduct);
        final ArrayAdapter<?> adapter =
                new ProductsAutoCompleteAdapter(mRootView.getContext(),
                        android.R.layout.simple_list_item_1,
                        Config.articles);
        t.setAdapter(adapter);
        t.setThreshold(1);
        t.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long position) {
                Article article = (Article) adapter.getItem((int) position);
                mReferenceText.setText(Integer.toString(article.getId()));
                mBarCode.setText("");
            }
        });

    }

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.buttonOk:
                saveInventory();
                break;
            case R.id.buttonCancel:
                clearView();
                Toast.makeText(this, "Annulé", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void saveInventory() {

        // read inventory
        int ref = 0;
        try {
            ref = Integer.parseInt(mReferenceText.getText().toString());
        } catch (NumberFormatException e) {
            Toast toast = Toast.makeText(this, "Référence non définie", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        int q = 0;
        try {
            q = Integer.parseInt(mQuantiteText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantité totale non définie", Toast.LENGTH_LONG).show();
            return;

        }

        int qv = 0;
        try {
            qv = Integer.parseInt(mQuantiteVenteText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantité à la vente non définie", Toast.LENGTH_LONG).show();
            return;

        }

        int etiNb = 0;
        try {
            etiNb = Integer.parseInt(mEtiquetteNbText.getText().toString());
        } catch (NumberFormatException e) {
        }

        InventoryItem i = new InventoryItem();
        i.setArticleId(ref);
        i.setQuantite(q);
        i.setQuantiteVente(qv);
        i.setLocalisation(mLocalisationText.getText().toString());
        i.setComment(mCommentext.getText().toString());
        i.setEtiquetteNb(etiNb);

        //save
        InventoryItemDAO dao = new InventoryItemDAO(mRootView.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.createVente(i);

        Toast.makeText(this, "Article ajouté:" + ref, Toast.LENGTH_LONG).show();

        clearView();
    }

    private void clearView() {

        mProductText.setText("", false);
        mBarCode.setText("");
        mReferenceText.setText("");
        mQuantiteText.setText("");
        mQuantiteVenteText.setText("");
        mLocalisationText.setText("");
        mCommentext.setText("");
        mEtiquetteNbText.setText("");
    }

}
