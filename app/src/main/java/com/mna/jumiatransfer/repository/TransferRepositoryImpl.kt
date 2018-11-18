package com.mna.jumiatransfer.repository

import com.mna.jumiatransfer.model.Transfer
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class TransferRepositoryImpl: TransferRepository {

    var disposables: CompositeDisposable? = null

    init {
        disposables = CompositeDisposable()
    }

    override fun transfer(amount: Int, currency: Currency, walletId: String, email: String) {

    }

    override fun clear() {
        disposables?.clear()
    }
}