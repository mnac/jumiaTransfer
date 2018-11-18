package com.mna.jumiatransfer.repository

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class UserRepositoryImpl(context: Context) : UserRepository {
    companion object {
        const val WALLET_IDS_KEY = "wallet_ids_key"
        const val EMAIL_KEY = "email_key"
    }

    private var disposables: CompositeDisposable

    private var walletIdsPreference: Preference<Set<String>>
    private var emailPreference: Preference<String>

    init {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val rxPreferences = RxSharedPreferences.create(preferences)
        disposables = CompositeDisposable()
        walletIdsPreference = rxPreferences.getStringSet(WALLET_IDS_KEY)
        emailPreference = rxPreferences.getString(EMAIL_KEY)
    }

    override fun updateEmail(email: String) {
        emailPreference.set(email)
    }

    override fun getEmail(callback: RepositoryCallback<String>) {
        disposables.add(emailPreference
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess))
    }

    override fun getWalletIdsHistory(callback: RepositoryCallback<Set<String>>) {
        disposables.add(walletIdsPreference
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess))
    }

    override fun addWalletIdInHistory(walletId: String) {
        val ids = walletIdsPreference.get()
        walletIdsPreference.set(ids.plus(walletId))
    }

    override fun clear() {
        disposables.clear()
    }
}