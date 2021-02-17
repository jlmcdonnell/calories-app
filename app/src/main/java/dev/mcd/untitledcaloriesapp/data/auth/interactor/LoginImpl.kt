package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.Login
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserCredentials
import javax.inject.Inject

class LoginImpl @Inject constructor(
    private val authApi: AuthApi,
    private val setUserCredentials: SetUserCredentials,
) : Login {

    override suspend fun execute(email: String, password: String) {
        val authResponse = authApi.login(email, password)
        setUserCredentials.execute(
            token = authResponse.accessToken,
            refresh = authResponse.refreshToken,
            expiresInMinutes = authResponse.expiresIn,
        )
    }
}
