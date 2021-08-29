package com.hk.transactionsapp.utils

import com.hk.transactionsapp.model.TransactionItem


fun generateTransactionItem(): TransactionItem {
    val id = "1"
    val transactionDate = "2021-02-02T08:11:16+13:00"
    val summary = "Fancy Food Market Auckland"
    val debit = 10.0
    val credit = 0.0
    val gst = 1.5

    return TransactionItem(
        id = id,
        transactionDate = transactionDate,
        summary = summary,
        debit = debit,
        credit = credit,
        gst = gst
    )
}