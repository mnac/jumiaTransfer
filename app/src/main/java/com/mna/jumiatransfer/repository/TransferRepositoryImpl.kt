package com.mna.jumiatransfer.repository

import android.annotation.SuppressLint
import com.mna.jumiatransfer.AppConstants.Companion.TEST_TIMER
import com.mna.jumiatransfer.AppConstants.Companion.TEST_USER_NAME
import com.mna.jumiatransfer.model.TransferStatus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TransferRepositoryImpl: TransferRepository {

    private var disposables: CompositeDisposable? = null

    init {
        disposables = CompositeDisposable()
    }

    @SuppressLint("CheckResult")
    override fun transfer(amount: Double, walletId: String, email: String,
                          callback: RepositoryCallback<TransferStatus>) {
        disposables?.add(Observable.timer(TEST_TIMER, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    callback.onSuccess(TransferStatus(TEST_USER_NAME, amount))
                })
    }

    override fun clear() {
        disposables?.clear()
    }
}