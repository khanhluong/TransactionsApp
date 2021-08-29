package com.hk.transactionsapp.utils

data class Resource<T>(val status: Status, var data: T? = null, val message: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> onSuccess(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> onError(message: String): Resource<T> {
            return Resource(Status.ERROR, message = message)
        }

        fun <T> onLoading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }
}