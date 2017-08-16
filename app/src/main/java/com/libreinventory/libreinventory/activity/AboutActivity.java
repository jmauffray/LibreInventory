package com.libreinventory.libreinventory.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.libreinventory.libreinventory.R;

public class AboutActivity extends Activity {

    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mRootView = getWindow().getDecorView();

        TextView aboutText = (TextView) mRootView.findViewById(R.id.editTextAbout);
        aboutText.setText("Source code available here :\n" +
                "https://github.com/jmauffray/LibreInventory\n" +
                "Version 0.1\n" +
                "Contact : jmauffray@gmail.com\n\n" +
                "== Quick manual ==\n" +
                "Copy products.csv file in android download directory with following csv format (header line and data lines)\n" +
                "reference,description,,,,,EAN13 barcode\n" +
                "123,article name,,,,,1234567890123\n\n" +
                "Launch LibreInventory application, import products and then start inventory.\n" +
                "Bold fields are mandatory.\n" +
                "When inventory finished you can export or send inventory in csv format.");

        aboutText.setText(getResources().getText(R.string.about));
    }
}
