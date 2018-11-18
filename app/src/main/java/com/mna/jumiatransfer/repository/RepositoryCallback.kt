package com.mna.jumiatransfer.repository

interface RepositoryCallback<T> {
    fun onSuccess(data: T)
}