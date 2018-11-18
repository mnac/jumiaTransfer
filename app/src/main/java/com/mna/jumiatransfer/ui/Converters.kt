package com.mna.jumiatransfer.ui

import androidx.databinding.InverseMethod
import java.text.NumberFormat


object Converters {
    @JvmStatic
    fun toPrice(value: String): Int {
        return try {
            val price = value.replace("[^\\d.]", "").trim()
            Integer.parseInt(price)
        } catch (e: NumberFormatException) {
            -1
        }
    }

    @JvmStatic
    @InverseMethod("toPrice")
    fun fromPrice(value: Int): String {
        val formatter = NumberFormat.getCurrencyInstance()
        return formatter.format(value)
    }
}
