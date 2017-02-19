package com.fuelbuddy.mobile.util;

import android.util.Log;

import com.fuelbuddy.mobile.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class PriceHelper {

    private static final String UNKOWN_PRICE = "";
    private static NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("PL"));
    private static NumberFormat percentNf;
    private static String currentCurrency = Config.CURRENCY_DK;

    static {
        percentNf = new DecimalFormat("#,##0.##%");
        percentNf.setMinimumFractionDigits(0);
        percentNf.setMaximumFractionDigits(2);
    }


    public static String generateFuelPriceWithCurrency(String fuelType, String price) {
        StringBuilder sb = new StringBuilder(fuelType);
        sb.append(".");
        sb.append("  ");
        sb.append(toFormattedPriceWithCurrency(price));
        return sb.toString();
    }

    public static String generateFuelPrice(String price) {
        StringBuilder sb = new StringBuilder();
        sb.append(toFormattedPrice(price));
        return sb.toString();
    }

    public static String toFormattedPriceWithCurrency(String price) {
        return toFormattedPriceWithCurrency(price, currentCurrency);
    }


    private static String toFormattedPrice(String price) {
        String to = UNKOWN_PRICE;
        if (price != null) {
            try {
                double dPrice = new BigDecimal(price).doubleValue();
                to = nf.format(dPrice);
                to = to.replace("\u00A4", "");
                to = to.replace(",", ".");
                StringBuilder sb = new StringBuilder(to);
                sb.deleteCharAt(to.length()-1);
                to = sb.toString();
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException", "" + e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException", "" + e.getMessage());
            }
        }
        return to;
    }

    private static String toFormattedPriceWithCurrency(String price, String currency) {
        if (price != null && currency != null) {
            try {
                double dPrice = new BigDecimal(price).doubleValue();
                String to = nf.format(dPrice);
                to = to.replace("\u00A4", currency);
                return to;
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException", "" + e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException", "" + e.getMessage());
            }
        }

        return UNKOWN_PRICE;
    }

}
