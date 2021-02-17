package dev.mcd.untitledcaloriesapp.data.common.extensions

import retrofit2.Response

fun Response<*>.asException(): Exception {
    return Exception("HTTP error: code=${code()} body=${errorBody()?.string()}")
}
