package dev.mcd.untitledcaloriesapp.data.prefs.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import dev.mcd.untitledcaloriesapp.domain.prefs.entity.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface UserPreferencesStore {
    suspend fun get(): UserPreferences
    suspend fun setUserToken(userToken: String)
    suspend fun getUserToken(): String?
    suspend fun setUserRefreshToken(refreshToken: String)
    suspend fun getUserRefreshToken(): String?
    suspend fun setUserTokenExpires(userTokenExpires: Long)
    suspend fun getUserTokenExpires(): Long?
    suspend fun setPendingConfirmation(pendingConfirmation: Boolean)
}

class UserPreferencesStoreImpl(context: Context) : UserPreferencesStore {

    companion object {
        private val userTokenKey = stringPreferencesKey("user_token")
        private val userTokenRefreshKey = stringPreferencesKey("user_token_refresh")
        private val userTokenExpiresKey = longPreferencesKey("user_token_expires")
        private val pendingConfirmationKey = booleanPreferencesKey("pending_confirmation")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user")

    override suspend fun get(): UserPreferences {
        return dataStore.data.map {
            UserPreferences(
                userToken = it[userTokenKey],
                userTokenRefresh = it[userTokenRefreshKey],
                userTokenExpires = it[userTokenExpiresKey] ?: 0,
                pendingConfirmation = it[pendingConfirmationKey] ?: false,
            )
        }.first()
    }

    override suspend fun setUserToken(userToken: String) {
        dataStore.edit {
            it[userTokenKey] = userToken
        }
    }

    override suspend fun getUserToken(): String? {
        return dataStore.data.first()[userTokenKey]
    }

    override suspend fun setPendingConfirmation(pendingConfirmation: Boolean) {
        dataStore.edit {
            it[pendingConfirmationKey] = pendingConfirmation
        }
    }

    override suspend fun setUserRefreshToken(refreshToken: String) {
        dataStore.edit {
            it[userTokenRefreshKey] = refreshToken
        }
    }

    override suspend fun getUserRefreshToken(): String? {
        return dataStore.data.first()[userTokenRefreshKey]
    }

    override suspend fun setUserTokenExpires(userTokenExpires: Long) {
        dataStore.edit {
            it[userTokenExpiresKey] = userTokenExpires
        }
    }

    override suspend fun getUserTokenExpires(): Long? {
        return dataStore.data.first()[userTokenExpiresKey]
    }
}
