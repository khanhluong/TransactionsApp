package com.hk.transactionsapp.domain.repository

import com.hk.transactionsapp.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getRemoteResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.onSuccess(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.onError("Network call has failed for a following reason: $message")
    }
}