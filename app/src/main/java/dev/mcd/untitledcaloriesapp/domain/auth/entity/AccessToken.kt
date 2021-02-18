package dev.mcd.untitledcaloriesapp.domain.auth.entity

data class AccessToken(
    val accessToken: String,
    val refreshToken: String,
    val tokenExpiry: Long,
)
