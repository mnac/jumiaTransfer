package com.mna.jumiatransfer.repository

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONArray


class UserRepositoryImpl(context: Context) : UserRepository {
    companion object {
        const val WALLET_IDS_KEY = "wallet_ids_key"
        const val EMAIL_KEY = "email_key"
    }

    private var disposables: CompositeDisposable

    private var walletIdsPreference: Preference<String>
    private var emailPreference: Preference<String>

    init {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val rxPreferences = RxSharedPreferences.create(preferences)

        disposables = CompositeDisposable()
        walletIdsPreference = rxPreferences.getString(WALLET_IDS_KEY)
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

    override fun getWalletIdsHistory(callback: RepositoryCallback<ArrayList<String>>) {
        disposables.add(walletIdsPreference
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    callback.onSuccess(deserializeIds(it))
                })
    }

    override fun addWalletIdInHistory(walletId: String) {
        val ids = LinkedHashSet<String>()
        ids.add(walletId)
        ids.addAll(deserializeIds(walletIdsPreference.get()))
        walletIdsPreference.set(JSONArray(ids).toString())
    }

    private fun deserializeIds(serializedIds: String): ArrayList<String>  {
        val ids = ArrayList<String>()

        if (serializedIds.isNotEmpty()) {
            val jsonArray = JSONArray(serializedIds)
            for (i in 0 until jsonArray.length()) {
                ids.add(jsonArray.get(i) as String)
            }
        }

        return ids
    }

    override fun clear() {
        disposables.clear()
    }
}