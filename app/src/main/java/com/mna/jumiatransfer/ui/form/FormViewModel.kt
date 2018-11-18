package com.mna.jumiatransfer.ui.form

import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mna.jumiatransfer.AppConstants


class FormViewModel : ViewModel() {
    val walletId = ObservableField<String>()
    val email = ObservableField<String>()

    fun isWalletIdEmpty(): Boolean {
        return walletId.get().isNullOrBlank()
    }

    fun isWalletIdInvalid(): Boolean {
        walletId.get()?.let {
            return it.length != AppConstants.WALLET_ID_LENGTH
        }
    }

    fun isEmailEmpty(): Boolean {
        email.get().let {
            return it.isNullOrBlank()
        }
    }

    fun isInvalidEmail(): Boolean {
        email.get()?.let {
            return !Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }
    }
}