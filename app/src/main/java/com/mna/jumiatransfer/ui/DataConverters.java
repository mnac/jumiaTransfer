package com.mna.jumiatransfer.ui;

import java.text.NumberFormat;

import androidx.databinding.InverseMethod;

public class DataConverters {

    public static int toPrice(String value) {
        try {
            String price = value.replaceAll("[^\\d.]", "").trim();
            return Integer.parseInt(price);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @InverseMethod("toPrice")
    public static String fromPrice(int value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }
}
