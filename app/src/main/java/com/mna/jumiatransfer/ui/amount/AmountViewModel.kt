package com.mna.jumiatransfer.ui.amount

import androidx.lifecycle.ViewModel


class AmountViewModel : ViewModel() {
    var amount: Int = 0

    fun isAmountEmpty(): Boolean {
        return true
    }

    fun isAmountInvalid(): Boolean {
        return false
    }
}