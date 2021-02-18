package dev.mcd.untitledcaloriesapp.data.auth.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface AccessTokenStore {
    suspend fun tokenChanges(): Flow<AccessToken?>
    suspend fun set(accessToken: AccessToken)
    suspend fun get(): AccessToken?
    suspend fun clear()
}

class AccessTokenStoreImpl(context: Context) : AccessTokenStore {
    companion object {
        private val accessTokenKey = stringPreferencesKey("access_token")
        private val refreshTokenKey = stringPreferencesKey("refresh_token")
        private val tokenExpiryKey = longPreferencesKey("token_expiry")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "access_token")

    override suspend fun set(accessToken: AccessToken) {
        dataStore.edit {
            it[accessTokenKey] = accessToken.accessToken
            it[refreshTokenKey] = accessToken.refreshToken
            it[tokenExpiryKey] = accessToken.tokenExpiry
        }
    }

    override suspend fun get(): AccessToken? {
        return dataStore.data.first().toAccessToken()
    }

    override suspend fun clear() {
        dataStore.data.first().toMutablePreferences().clear()
    }

    override suspend fun tokenChanges(): Flow<AccessToken?> {
        return dataStore.data.map { it.toAccessToken() }
    }

    private fun Preferences.toAccessToken(): AccessToken? {
        return AccessToken(
            accessToken = this[accessTokenKey] ?: return null,
            refreshToken = this[refreshTokenKey] ?: return null,
            tokenExpiry = this[tokenExpiryKey] ?: return null,
        )
    }
}
