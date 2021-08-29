package com.hk.transactionsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource

interface TransactionsUseCase {
    fun getTransactions(): LiveData<Resource<List<TransactionItem>>>
}