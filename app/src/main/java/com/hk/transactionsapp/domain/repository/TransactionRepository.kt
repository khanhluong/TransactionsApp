package com.hk.transactionsapp.domain.repository

import androidx.lifecycle.LiveData
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource

interface TransactionRepository {
    fun getTransactions(): LiveData<Resource<TransactionItem>>
}