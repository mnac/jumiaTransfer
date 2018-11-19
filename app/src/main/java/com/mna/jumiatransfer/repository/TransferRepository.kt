package com.mna.jumiatransfer.repository

import com.mna.jumiatransfer.model.TransferStatus

interface TransferRepository: Repository {
    fun transfer(amount: Double, walletId: String, email: String, callback: RepositoryCallback<TransferStatus>)
}