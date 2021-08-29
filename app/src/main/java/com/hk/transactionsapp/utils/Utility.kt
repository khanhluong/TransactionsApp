package com.hk.transactionsapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertDate(dateString: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(parser.parse(dateString)!!)
}