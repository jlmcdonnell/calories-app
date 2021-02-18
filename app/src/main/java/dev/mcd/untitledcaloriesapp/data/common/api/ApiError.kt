package dev.mcd.untitledcaloriesapp.data.common.api

sealed class ApiError(message: String, cause: Throwable? = null) : Exception(message, cause) {
    object Unauthorized : Exception("HTTP error 401 was returned")
}
