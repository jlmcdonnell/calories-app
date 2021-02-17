package dev.mcd.untitledcaloriesapp.domain.prefs.entity

data class UserPreferences(
    val userToken: String? = null,
    val userTokenRefresh: String? = null,
    val userTokenExpires: Long = 0L,
    val pendingConfirmation: Boolean = false,
)
