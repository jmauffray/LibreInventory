package com.libreinventory.libreinventory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.db.InventoryItemDAO;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.model.InventoryItem;
import com.libreinventory.libreinventory.widget.ProductsAutoCompleteAdapter;

import java.sql.SQLException;


/**
 * A login screen that offers login via email/password.
 */
public class InventoryActivity extends Activity implements OnClickListener {


    private View mRootView;

    // UI references.
    private EditText mReferenceText;
    private EditText mQuantiteText;
    private EditText mLocalisationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        mRootView = getWindow().getDecorView();

        //button listeners
        Button b = (Button) mRootView.findViewById(R.id.buttonOk);
        b.setOnClickListener(this);

        b = (Button) mRootView.findViewById(R.id.buttonCancel);
        b.setOnClickListener(this);

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
                setInteger(mRootView, R.id.editTextRef, article.getId());
            }
        });

    }

    @Override
    public void onClick(View arg0) {

        Log.e("log click", "aaaa");
        switch (arg0.getId()) {
            case R.id.buttonOk:
                saveInventory();
                break;
            case R.id.buttonCancel:
                break;
            default:
                break;
        }
    }

    private void saveInventory() {

        // read inventory
        EditText refStr = (EditText) mRootView.findViewById(R.id.editTextRef);
        int ref = Integer.parseInt(refStr.getText().toString());

        EditText qStr = (EditText) mRootView.findViewById(R.id.editTextQuantite);
        int q = Integer.parseInt(qStr.getText().toString());

        EditText locStr = (EditText) mRootView.findViewById(R.id.editTextLoc);
        String loc = locStr.getText().toString();

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

    }

    private void setString(View v, int id, String value) {

        EditText e = (EditText) v.findViewById(id);
        e.setText(value);
    }

    private void setInteger(View v, int id, int value) {

        TextView e = (TextView) v.findViewById(id);
        e.setText(Integer.toString(value));
    }

    private void setFloat(View v, int id, float value) {

        TextView e = (TextView) v.findViewById(id);
        e.setText(Float.toString(value));
    }
}

