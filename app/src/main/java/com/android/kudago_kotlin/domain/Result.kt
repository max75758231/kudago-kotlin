package com.android.kudago_kotlin.domain

sealed class Result<T>(
    val data: T? = null,
    val errorEntity: ErrorEntity? = null
) {
    class Success<T>(data: T): Result<T>(data)
    class Loading<T>(data: T? = null): Result<T>(data)
    class Error<T>(errorEntity: ErrorEntity, data: T? = null): Result<T>(data, errorEntity)
}

sealed class ErrorEntity {
    object NetworkError : ErrorEntity()
    object EmptyListError : ErrorEntity()
    object ServiceUnavailableError : ErrorEntity()
    object UnknownError : ErrorEntity()
}