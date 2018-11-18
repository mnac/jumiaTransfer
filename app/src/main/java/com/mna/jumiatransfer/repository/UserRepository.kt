package com.mna.jumiatransfer.repository

interface UserRepository: Repository {
    fun updateEmail(email: String)
    fun getEmail(callback: RepositoryCallback<String>)
    fun addWalletIdInHistory(walletId: String)
    fun getWalletIdsHistory(callback: RepositoryCallback<Set<String>>)
}