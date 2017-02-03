package com.fuelbuddy.mobile.widget;

import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.fuelbuddy.mobile.editprice.UpdateActivity;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by zjuroszek on 22.01.17.
 */

public class PriceTextListener extends MaskedTextChangedListener {

    MaskedTextChangedListener.ValueListener valueListener = new MaskedTextChangedListener.ValueListener() {
        @Override
        public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
            Log.d(UpdateActivity.class.getSimpleName(), extractedValue);
            Log.d(UpdateActivity.class.getSimpleName(), String.valueOf(maskFilled));
        }
    };

    public PriceTextListener(@NotNull String format, boolean autocomplete, @NotNull EditText field, @Nullable TextWatcher listener, @Nullable ValueListener valueListener, ValueListener valueListener1) {
        super(format, autocomplete, field, listener, valueListener);
        this.valueListener = valueListener1;
    }

    public PriceTextListener(@NotNull String format, boolean autocomplete, @NotNull EditText field, @Nullable TextWatcher listener, @Nullable ValueListener valueListener) {
        super(format, autocomplete, field, listener, valueListener);
    }


}
