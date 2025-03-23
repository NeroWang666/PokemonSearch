package com.example.pokemonsearch.model

sealed class Result<out R> {
    data class Success<out R>(val data: R, val hasMore: Boolean = false) : Result<R>()
    data class Error<out R>(val data: R? = null, val exception: Throwable) : Result<R>()
    data class Loading<out R>(val data: R? = null, val isLoadingMore: Boolean = false) : Result<R>()

    fun data(): R? {
        return when (this) {
            is Success -> data
            is Loading -> data
            is Error -> data
        }
    }
}
