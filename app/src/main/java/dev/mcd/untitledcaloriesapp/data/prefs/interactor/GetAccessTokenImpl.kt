package dev.mcd.untitledcaloriesapp.data.prefs.interactor

import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStore
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetAccessToken
import java.util.*
import javax.inject.Inject

class GetAccessTokenImpl @Inject constructor(private val userPreferencesStore: UserPreferencesStore) : GetAccessToken {
    override suspend fun execute(): AccessToken? {
        val token = userPreferencesStore.getUserToken()
        val refresh = userPreferencesStore.getUserRefreshToken()
        val expiry = userPreferencesStore.getUserTokenExpires()

        if (token != null && refresh != null && expiry != null) {
            return AccessToken(token, refresh, Date(expiry))
        }
        return null
    }
}
