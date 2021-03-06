package com.mna.jumiatransfer.ui.amount

import androidx.lifecycle.ViewModel


class AmountViewModel : ViewModel() {
    var amount: Double = 0.0

    fun isAmountEmpty(): Boolean {
        return true
    }

    fun isAmountInvalid(): Boolean {
        return false
    }
}