package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.GetAuthenticationState
import javax.inject.Inject

class GetAuthenticationStateImpl @Inject constructor(
    private val accessTokenStore: AccessTokenStore
) : GetAuthenticationState {
    override suspend fun execute(): AuthenticationState {
        return if (accessTokenStore.get() != null) {
            AuthenticationState.Authenticated
        } else {
            AuthenticationState.NotAuthenticated
        }
    }
}
