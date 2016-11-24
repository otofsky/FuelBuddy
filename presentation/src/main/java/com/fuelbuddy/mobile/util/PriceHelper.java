package com.fuelbuddy.mobile.util;

import android.util.Log;

import com.fuelbuddy.mobile.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class PriceHelper {

    private static final String UNKOWN_PRICE = "";
    private static final double EPSILON = 0.00000000001;
    private static final int SCALE = 4;
    private static NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("PL"));
    private static NumberFormat percentNf;
    private static String currentCurrency = Config.CURRENCY_DKR;

    static {
        percentNf = new DecimalFormat("#,##0.##%");
        percentNf.setMinimumFractionDigits(0);
        percentNf.setMaximumFractionDigits(2);
    }

    public static String toFormattedPrice(String price, String currency) {
        if (price != null && currency != null) {
            try {

                double dPrice = new BigDecimal(price).doubleValue();
                String to = nf.format(dPrice);
                to = to.replace("\u00A4", currency);
                return to;
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            }
        }

        return UNKOWN_PRICE;
    }




    public static String toMYFormattedPrice(String price) {
        if (price != null) {
            try {
                double dPrice = new BigDecimal(price).doubleValue();
                String to = nf.format(dPrice);
                to.substring(0, to.length() - 1);
                to = to.replace("\u00A4", "");
                return to;
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            }

        }
        return UNKOWN_PRICE;
    }



    public static String toFormattedPrice(String price) {
        return toFormattedPrice(price, currentCurrency);
    }



    public static String toFormattedPrice(BigDecimal price) {
        return toFormattedPrice(price.toString());
    }

    public static int toIntegerFormattedAmount(String amount) {
        Double dAmount = new BigDecimal(amount).doubleValue();
        return dAmount.intValue();
    }


    public static boolean isZeroPrice(String price) {
        if (price != null) {
            try {
                double dPrice = new BigDecimal(price).doubleValue();
                return (dPrice <= EPSILON);
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            }
        }
        return true;
    }

    public static boolean isPLN(String currency) {
        return Config.CURRENCY_DKR.equals(currency) || "".equals(currency);
    }

    public static String toPercent(String value) {
        if (value != null) {
            try {
//		L.d("toPercent: " + getValue);
                BigDecimal bd = new BigDecimal(value).divide(new BigDecimal("100"), SCALE, BigDecimal.ROUND_HALF_UP);
                String ret = percentNf.format(bd.doubleValue());
                return ret;
            } catch (NumberFormatException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            } catch (NullPointerException e) {
                Log.e("NumberFormatException",""+ e.getMessage());
            }
        }
        return UNKOWN_PRICE;
    }

    public static float getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return proportion * 100;
    }


}
