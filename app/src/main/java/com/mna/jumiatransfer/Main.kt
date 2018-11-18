package com.mna.jumiatransfer

import com.mna.jumiatransfer.repository.TransferRepository
import com.mna.jumiatransfer.repository.UserRepository

interface Main {
    fun getUserRepository(): UserRepository
    fun getTransferRepository(): TransferRepository
}