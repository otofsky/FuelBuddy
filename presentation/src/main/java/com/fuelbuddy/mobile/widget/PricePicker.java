package com.fuelbuddy.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fuelbuddy.mobile.R;
import com.fuelbuddy.mobile.model.FuelModel;
import com.fuelbuddy.mobile.util.PriceHelper;

import java.util.Date;

/**
 * Created by zjuroszek on 30.12.16.
 */

public class PricePicker extends LinearLayout implements SeekBar.OnSeekBarChangeListener {
    private CheckBox checkBox;
    private Date date;
    //private Fuel fuel;
    //private IPricePickerListener listener;
    private int min;
    private TextView priceDateView;
    private TextView priceView;
    private SeekBar seekBar;
    private ViewGroup seekBarPanel;
    private int size;
    private Integer value;

    /* renamed from: com.pumpdroid.widget.PricePicker.1 */
/*    class C04521 implements OnClickListener {
        C04521() {
        }

        public void onClick(View view) {
            com.pumpdroid.widget.PricePicker.this.adjust(-1);
        }
    }*/

    /* renamed from: com.pumpdroid.widget.PricePicker.2 */
/*
    class C04532 implements OnClickListener {
        C04532() {
        }

        public void onClick(View view) {
            com.pumpdroid.widget.PricePicker.this.adjust(1);
        }
    }
*/

    /* renamed from: com.pumpdroid.widget.PricePicker.3 */
/*
    class C04543 implements CompoundButton.OnCheckedChangeListener {
        C04543() {
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (com.pumpdroid.widget.PricePicker.this.listener != null) {
                com.pumpdroid.widget.PricePicker.this.listener.activeStatusChanged();
            }
            com.pumpdroid.widget.PricePicker.this.seekBarPanel.setVisibility(z ? 0 : 8);
        }
    }
*/

    public PricePicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.min = 300;
        this.value = Integer.valueOf(0);
        this.size = 50;
        View inflate = inflate(context, R.layout.price_picker, this);
       /* Settings settings = new Settings(context);
        if (inflate != null && Build.VERSION.SDK_INT >= 16) {
            if (settings.getTheme() == C0329R.style.PumpdroidThemeDark) {
                inflate.setBackground(context.getResources().getDrawable(C0329R.drawable.card_dark));
            } else if (settings.getTheme() == C0329R.style.PumpdroidThemeBlack) {
                inflate.setBackground(context.getResources().getDrawable(C0329R.drawable.card_black));
            } else {
                inflate.setBackground(context.getResources().getDrawable(C0329R.drawable.card));
            }
        }*/
        this.priceView = (TextView) inflate.findViewById(R.id.pump_price);
        this.priceDateView = (TextView) inflate.findViewById(R.id.pump_price_date);
        this.checkBox = (CheckBox) inflate.findViewById(R.id.fuel_checkbox);
        this.seekBar = (SeekBar) inflate.findViewById(R.id.pump_price_seekbar);
        this.seekBar.setOnSeekBarChangeListener(this);
        // inflate.findViewById(R.id.btn_minus).setOnClickListener(new com.pumpdroid.widget.PricePicker.C04521());
        //inflate.findViewById(R.id.btn_plus).setOnClickListener(new com.pumpdroid.widget.PricePicker.C04532());
        this.seekBarPanel = (ViewGroup) inflate.findViewById(R.id.seekbar_panel);

        // this.checkBox.setOnCheckedChangeListener(new com.pumpdroid.widget.PricePicker.C04543());
    }

    public PricePicker(Context context) {
        this(context, null);
    }

    public void init(FuelModel fuelModel) {
        //  PriceRange range = settings.getRange(fuel);
        //this.min = range.getMin() * 10;
        // this.size = (range.getMax() * 10) - this.min;
        // this.value = d != null ? Integer.valueOf((int) ((d.doubleValue() * 10.0d) - ((double) this.min))) : null;
        this.date = date;
        //this.fuel = fuel;
        this.checkBox.setChecked(true);
        //this.seekBarPanel.setVisibility(z ? 0 : 8);
        this.checkBox.setText(fuelModel.getFuelName());

        priceView.setText(PriceHelper.toFormattedPrice(fuelModel.getPrice()));


        //this.checkBox.setTextColor(settings.getTextColor());
        //adjust(0);
    }

/*    private void adjust(int i) {
        if (i != 0) {
            if (this.date == null) {
                this.value = Integer.valueOf(0);
            }
            this.date = new Date();
        }
        if (this.value != null && this.value.intValue() + i <= this.size && this.value.intValue() + i >= 0) {
            if (!(i == 0 || this.listener == null)) {
                this.listener.activeStatusChanged();
            }
            this.value = Integer.valueOf(this.value.intValue() + i);
            this.seekBar.setProgress((int) (((float) this.value.intValue()) * (100.0f / ((float) this.size))));
            updatePrice();
        }
    }*/

/*    private void updatePrice() {
        this.priceDateView.setText(this.date == null ? BuildConfig.VERSION_NAME : PumpItemAdapter.formatDate(this.date, getResources()));
        this.priceView.setTextColor(PriceDecorator.calculateColor(getResources(), this.date));
        this.priceView.setText((((float) (this.min + this.value.intValue())) / 10.0f) + "0");
    }*/

/*    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            this.date = new Date();
            this.value = Integer.valueOf((int) (((float) i) * (((float) this.size) / 100.0f)));
            updatePrice();
            if (this.listener != null) {
                this.listener.activeStatusChanged();
            }
        }
    }*/

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public Integer getValue() {
        return this.value;
    }

    public Double getRealValue() {
        if (this.value == null) {
            return null;
        }
        return Double.valueOf(((double) (this.min + this.value.intValue())) / 10.0d);
    }

/*    public Fuel getFuel() {
        return this.fuel;
    }

    public boolean isActive() {
        return this.checkBox != null && this.checkBox.isChecked();
    }

    public void setListener(IPricePickerListener iPricePickerListener) {
        this.listener = iPricePickerListener;
    }*/
}
