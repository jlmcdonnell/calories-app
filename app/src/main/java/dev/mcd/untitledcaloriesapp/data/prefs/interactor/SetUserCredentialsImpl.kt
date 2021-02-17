package dev.mcd.untitledcaloriesapp.data.prefs.interactor

import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStore
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserCredentials
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SetUserCredentialsImpl @Inject constructor(private val preferences: UserPreferencesStore) : SetUserCredentials {
    override suspend fun execute(token: String, refresh: String, expiresInMinutes: Long) {
        val ttl = TimeUnit.MINUTES.toMicros(expiresInMinutes)
        val expiryTime = System.currentTimeMillis() + ttl

        with(preferences) {
            setUserToken(token)
            setUserRefreshToken(refresh)
            setUserTokenExpires(expiryTime)
            setPendingConfirmation(false)
        }
    }
}
