package dev.mcd.untitledcaloriesapp.domain.prefs.interactor

interface SetUserCredentials {
    suspend fun execute(token: String, refresh: String, expiresInMinutes: Long)
}
