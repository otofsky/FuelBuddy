package com.fuelbuddy.mobile.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.fuelbuddy.mobile.R;


public final class DialogFactory {



    public static Dialog createSimpleOkDialog(Context context, String title, String message,String neutralBtnMessage, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(neutralBtnMessage, listener);
        return alertDialog.create();
    }

 /*   public static Dialog createSimpleOkDialog(Context context,
                                              @StringRes int titleResource,
                                              @StringRes int messageResource) {

        return createSimpleOkDialog(context,
                context.getString(titleResource),
                context.getString(messageResource));
    }
*/
    public static Dialog createGenericErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_error_title))
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static Dialog createGenericErrorDialog(Context context, @StringRes int messageResource) {
        return createGenericErrorDialog(context, context.getString(messageResource));
    }

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }

}
