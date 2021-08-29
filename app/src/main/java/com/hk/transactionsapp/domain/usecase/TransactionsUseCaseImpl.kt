package com.hk.transactionsapp.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.hk.transactionsapp.domain.repository.TransactionRepositoryImpl
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource
import javax.inject.Inject

class TransactionsUseCaseImpl @Inject constructor(val repositoryImpl: TransactionRepositoryImpl) :
    TransactionsUseCase {


    var transactionsLiveData = liveData {
        emit(Resource.onLoading())
        val getTransactionsData = repositoryImpl.getTransactions().data?.let {
            Resource.onSuccess(it)
        }
        val transactionListLiveData: MutableLiveData<Resource<List<TransactionItem>>> = MutableLiveData()
        transactionListLiveData.value = getTransactionsData
        emitSource(transactionListLiveData)

    }

    override fun getTransactions(): LiveData<Resource<List<TransactionItem>>> = transactionsLiveData
}