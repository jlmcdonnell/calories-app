package dev.mcd.untitledcaloriesapp.domain.auth.entity

import java.util.*

data class AccessToken(
    val token: String,
    val refresh: String,
    val expiryDate: Date,
)
