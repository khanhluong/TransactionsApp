package com.hk.transactionsapp.features.transactions

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hk.transactionsapp.domain.usecase.TransactionsUseCase
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransactionsViewModel @Inject constructor(private val transactionsUseCase: TransactionsUseCase) :
    ViewModel() {
    lateinit var transactions: LiveData<Resource<List<TransactionItem>>>

    init {
        viewModelScope.launch {
            loadTransactions()
        }
    }

    @VisibleForTesting
    fun loadTransactions() {
        transactions = transactionsUseCase.getTransactions()
    }

}