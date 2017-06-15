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