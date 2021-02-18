package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.SignUp
import javax.inject.Inject

class SignUpImpl @Inject constructor(
    private val authApi: AuthApi,
    private val accessTokenStore: AccessTokenStore,
) : SignUp {

    override suspend fun execute(email: String, password: String) {
        val accessToken = authApi.signUp(email, password)
        accessTokenStore.set(accessToken)
    }
}
