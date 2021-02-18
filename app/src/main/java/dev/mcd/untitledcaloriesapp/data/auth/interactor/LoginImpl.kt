package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.Login
import javax.inject.Inject

class LoginImpl @Inject constructor(
    private val authApi: AuthApi,
    private val accessTokenStore: AccessTokenStore,
) : Login {

    override suspend fun execute(email: String, password: String) {
        val accessToken = authApi.login(email, password)
        accessTokenStore.set(accessToken)
    }
}
