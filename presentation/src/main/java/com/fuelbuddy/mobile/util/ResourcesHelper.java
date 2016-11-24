package com.fuelbuddy.mobile.util;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by pliszka on 23.06.16.
 */
public class ResourcesHelper {

    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static void setTextStyle(TextView textView, Context context, int id) {
        if (id != 0)
            if (Build.VERSION.SDK_INT < 23) {
                textView.setTextAppearance(context, id);
            } else {
                textView.setTextAppearance(id);
            }
    }

    public static void setEditTextStyle(EditText editText, Context context, int id) {
        if (id != 0)
            if (Build.VERSION.SDK_INT < 23) {
                editText.setTextAppearance(context, id);
            } else {
                editText.setTextAppearance(id);
            }
    }

    public static void setCheckBoxStyle(CheckBox checkBox, Context context, int id) {
        if (id != 0)
            if (Build.VERSION.SDK_INT < 23) {
                checkBox.setTextAppearance(context, id);
            } else {
                checkBox.setTextAppearance(id);
            }
    }

    public static void setButtonStyle(Button button, int id) {
        if (id != 0)
            button.setBackgroundResource(id);
    }
}
