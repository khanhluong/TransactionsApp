package com.hk.transactionsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionItem(
    val credit: Double,
    val debit: Double,
    val gst: Double,
    val id: String,
    val summary: String,
    val transactionDate: String
) : Parcelable