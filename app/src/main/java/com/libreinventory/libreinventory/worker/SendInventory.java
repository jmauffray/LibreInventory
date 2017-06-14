package com.libreinventory.libreinventory.worker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SendInventory extends ExportInventory {

    private Activity mActivity;

    public SendInventory(Context context, Activity activity) {
        super(context);
        mActivity = activity;
    }

    protected void onPostExecute(String data) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        composeEmail(
                new String[]{""},
                "Libre Inventory",
                "Inventaire en pièce jointe",
                Uri.fromFile(mFile));
    }

    private void composeEmail(String[] addresses,
                              String subject,
                              String message,
                              Uri attachment) {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, addresses);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.putExtra(Intent.EXTRA_STREAM, attachment);
        email.setType("message/rfc822");
        mActivity.startActivity(Intent.createChooser(email, "Choisir un client Email :"));
    }

}