package com.fuelbuddy.mobile.util;

import android.view.View;
import android.widget.TextView;

/**
 * Created by zjuroszek on 06.07.16.
 */
public class ViewHelper {

    public static void setVisible(View view, boolean isVisible) {
        setVisible(view, isVisible, false);
    }

    public static void setVisible(View view, boolean isVisible, boolean isInvisble) {
        if(view==null) {
            return;
        }
        if(isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(isInvisble ? View.INVISIBLE : View.GONE);
        }
    }
    public static void setTextIfNotNull(TextView textView, Integer value) {
        String text = null;
        if(value!=null) {
            text = String.valueOf(value);
        }
        setTextIfNotNull(textView, text);
    }

    public static void setTextIfNotNull(TextView textView, String text) {
        if(textView!=null) {
            if(text==null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(text);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }
}
