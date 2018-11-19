package com.mna.jumiatransfer.ui;

import android.view.View;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseMethod;

public class DataConverters {

    public static double toPrice(String value) {
        try {
            String price = value.replaceAll("[^0-9.,]", "").trim();
            String priceFormatted = price.replaceAll(",", ".").trim();
            return Double.parseDouble(priceFormatted);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @InverseMethod("toPrice")
    public static String fromPrice(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) formatter).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) formatter).setDecimalFormatSymbols(decimalFormatSymbols);
        return formatter.format(value);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }

}
