package com.mna.jumiatransfer.repository

import java.util.*

interface TransferRepository: Repository {
    fun transfer(amount: Int, currency: Currency, walletId: String, email: String)
}