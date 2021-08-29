package com.hk.transactionsapp.domain.repository

import com.hk.transactionsapp.domain.TransactionsService
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionsService: TransactionsService) :
    BaseDataSource() {
    suspend fun getTransactions() = getRemoteResult { transactionsService.getTransactions() }

}