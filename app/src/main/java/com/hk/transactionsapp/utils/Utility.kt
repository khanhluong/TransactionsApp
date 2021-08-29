package com.hk.transactionsapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertDate(dateString: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(parser.parse(dateString)!!)
}

fun calculatorGST(credit: Double, debit: Double): Double {
    return if (credit != 0.0) {
        credit * .15
    } else {
        debit * .15
    }
}