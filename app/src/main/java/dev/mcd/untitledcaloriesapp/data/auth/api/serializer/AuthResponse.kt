package dev.mcd.untitledcaloriesapp.data.auth.api.serializer

import com.squareup.moshi.Json

data class AuthResponse(
    @field:Json(name = "access_token")
    val accessToken: String,
    @field:Json(name = "expires_in")
    val expiresInMinutes: Long,
    @field:Json(name = "refresh_token")
    val refreshToken: String,
)
