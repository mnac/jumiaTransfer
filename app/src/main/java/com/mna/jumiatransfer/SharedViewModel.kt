package com.mna.jumiatransfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat


class SharedViewModel : ViewModel() {
    var email = MutableLiveData<String>()
    var walletId = MutableLiveData<String>()
    var amount = MutableLiveData<Double>()

    fun getEmail(): String {
        return email.value ?: ""
    }

    fun getWalletId(): String {
        return walletId.value ?: ""
    }

    fun getAmount(): Double {
        return amount.value ?: 0.0
    }

    fun getAmountDisplay(): String {
        val value = amount.value ?: 0.0
        val format = NumberFormat.getCurrencyInstance()
        return format.format(value)
    }
}