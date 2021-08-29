package com.hk.transactionsapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hk.transactionsapp.domain.usecase.TransactionsUseCase
import com.hk.transactionsapp.model.TransactionItem

class FakeTransactionUseCase : TransactionsUseCase {


    private val resourceItem = Resource<List<TransactionItem>>(Resource.Status.LOADING)

    private val observableTransactionsItem = MutableLiveData(resourceItem)

    override fun getTransactions(): LiveData<Resource<List<TransactionItem>>> {
        return observableTransactionsItem
    }

    fun setReturnError() {
        observableTransactionsItem.value = Resource.onError("Error loading transactions")
    }

    fun setResourceItems(transactionsItem: List<TransactionItem>) {
        observableTransactionsItem.value = Resource.onSuccess(transactionsItem)
    }
}