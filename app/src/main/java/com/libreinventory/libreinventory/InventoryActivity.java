package com.libreinventory.libreinventory;

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
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText mLocalisationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mRootView = getWindow().getDecorView();

        mProductText = (AutoCompleteTextView) mRootView.findViewById(R.id.autoCompleteTextProduct);
        mBarCode = (EditText) mRootView.findViewById(R.id.editTextBarCode);
        mReferenceText = (EditText) mRootView.findViewById(R.id.editTextRef);
        mQuantiteText = (EditText) mRootView.findViewById(R.id.editTextQuantite);
        mLocalisationText = (EditText) mRootView.findViewById(R.id.editTextLoc);

        //button listeners
        Button b = (Button) mRootView.findViewById(R.id.buttonOk);
        b.setOnClickListener(this);

        b = (Button) mRootView.findViewById(R.id.buttonCancel);
        b.setOnClickListener(this);

        mBarCode.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()) {
                    return;
                }
                    for(Article a:Config.articles)
                    {
                        if(a.getBarCode().equals(s.toString()))
                        {
                            mReferenceText.setText(String.valueOf(a.getId()));
                            break;
                        }
                    }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {}
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
                Toast.makeText(this, "Annulé", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void saveInventory() {

        // read inventory
        int ref = 0;
        try
        {
            ref = Integer.parseInt(mReferenceText.getText().toString());
        } catch (NumberFormatException e)
        {
            Toast.makeText(this, "Référence non définie", Toast.LENGTH_SHORT).show();
            return;
        }

        int q = 0;
        try
        {
             q = Integer.parseInt(mQuantiteText.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Quantité non définie", Toast.LENGTH_SHORT).show();
            return;

        }
        String loc = mLocalisationText.getText().toString();

        InventoryItem i = new InventoryItem();
        i.setArticleId(ref);
        i.setQuantite(q);
        i.setLocalisation(loc);

        //save
        InventoryItemDAO dao = new InventoryItemDAO(mRootView.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.createVente(i);

        Toast.makeText(this, "Article ajouté:" + ref, Toast.LENGTH_SHORT).show();

        clearView();
    }

    private void clearView() {

        mProductText.setText("", false);
        mBarCode.setText("");
        mReferenceText.setText("");
        mQuantiteText.setText("1");
        mLocalisationText.setText("");
    }

}
