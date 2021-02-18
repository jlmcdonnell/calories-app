package dev.mcd.untitledcaloriesapp.data.common.extensions

import dev.mcd.untitledcaloriesapp.data.common.api.ApiError
import retrofit2.Response

fun Response<*>.asException(): Exception {
    return when (code()) {
        401 -> ApiError.Unauthorized
        else -> Exception("HTTP error: code=${code()} body=${errorBody()?.string()}")
    }
}
