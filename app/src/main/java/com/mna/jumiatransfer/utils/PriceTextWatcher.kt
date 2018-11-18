package com.mna.jumiatransfer.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat

class PriceTextWatcher(private val priceEdtTxt: EditText) : TextWatcher {
    private var formattedPrice: String? = null

    init {
        this.priceEdtTxt.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (priceEdtTxt.text.isNotEmpty() && priceEdtTxt.toString() != formattedPrice) {

            priceEdtTxt.removeTextChangedListener(this)

            val cleanString = priceEdtTxt.text.toString().trim().split("[,.]+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].replace("[\\D]".toRegex(), "")

            try {
                val price = cleanString.toDouble()
                formattedPrice = formatPriceValueForDisplay(price)
                priceEdtTxt.setText(formattedPrice)
            } catch (nfe: NumberFormatException) {
                formattedPrice = null
            }

            var cursorPosition = start + count


            if (before > 0 && s.length == 4 && start <= 2) {
                // user is going back into hundreds
                cursorPosition--
            } else if (s.length == 4 && start >= 1 && start < s.length) {
                // user is reaching thousands
                cursorPosition++
            }

            // ensure cursor position is into edit text bounds
            if (cursorPosition < 0 || formattedPrice.isNullOrBlank()) {
                cursorPosition = 0
            } else if (cursorPosition > formattedPrice!!.length) {
                cursorPosition = formattedPrice!!.length
            }

            priceEdtTxt.setSelection(cursorPosition)
            priceEdtTxt.addTextChangedListener(this)
        }
    }

    private fun formatPriceValueForDisplay(price: Double): String {
        try {
            if (price > 0) {
                val numberFormat = NumberFormat.getCurrencyInstance()

                val decimal = price - Math.floor(price)

                if (decimal > 0) {
                    numberFormat.maximumFractionDigits = 2
                } else {
                    numberFormat.maximumFractionDigits = 0
                }


                val decimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
                decimalFormatSymbols.currencySymbol = ""
                numberFormat.decimalFormatSymbols = decimalFormatSymbols

                var formattedPrice = numberFormat.format(price)

                // remove last non-break space if necessary
                if (formattedPrice.endsWith("\u00a0")) {
                    formattedPrice = formattedPrice.substring(0, formattedPrice.length - 1)
                }

                return formattedPrice.trim { it <= ' ' }
            } else {
                return ""
            }
        } catch (nfe: NumberFormatException) {
            return price.toString()
        }

    }

    override fun afterTextChanged(s: Editable) {}
}