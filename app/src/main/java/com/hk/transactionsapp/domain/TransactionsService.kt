package com.hk.transactionsapp.domain

import com.hk.transactionsapp.model.TransactionItem
import retrofit2.Response
import retrofit2.http.GET

interface TransactionsService {
    @GET("transactions")
    suspend fun getTransactions(): Response<List<TransactionItem>>
}