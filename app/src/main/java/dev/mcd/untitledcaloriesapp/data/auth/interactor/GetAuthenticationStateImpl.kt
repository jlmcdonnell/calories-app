package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState.Authenticated
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState.NotAuthenticated
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState.RefreshRequired
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.GetAuthenticationState
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import timber.log.Timber
import javax.inject.Inject

class GetAuthenticationStateImpl @Inject constructor(
    private val timeProvider: DateTimeProvider,
    private val accessTokenStore: AccessTokenStore,
) : GetAuthenticationState {
    override suspend fun execute(): AuthenticationState {
        val token = accessTokenStore.get()

        if (token == null) {
            Timber.d("not authed")
            return NotAuthenticated
        }

        return if (timeProvider.now >= token.tokenExpiry) {
            RefreshRequired
        } else {
            Authenticated
        }
    }
}
