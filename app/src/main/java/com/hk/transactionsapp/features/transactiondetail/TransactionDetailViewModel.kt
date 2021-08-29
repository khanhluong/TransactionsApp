package com.hk.transactionsapp.features.transactiondetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.calculatorGST
import com.hk.transactionsapp.utils.convertDate
import javax.inject.Inject

class TransactionDetailViewModel @Inject constructor() : ViewModel() {

    fun convertTransactionToDetail(transactionItem: TransactionItem): TransactionItem {
        return TransactionItem(
            id = transactionItem.id,
            credit = transactionItem.credit,
            debit = transactionItem.debit,
            summary = transactionItem.summary,
            transactionDate = convertDate(transactionItem.transactionDate),
            gst = calculatorGST(transactionItem.credit, transactionItem.debit)
        )
    }

    val transactionItem: MutableLiveData<TransactionItem> by lazy {
        MutableLiveData<TransactionItem>()
    }
}