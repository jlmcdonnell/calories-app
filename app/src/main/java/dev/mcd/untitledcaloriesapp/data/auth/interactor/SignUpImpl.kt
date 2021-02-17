package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.domain.auth.interactor.SignUp
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserCredentials
import javax.inject.Inject

class SignUpImpl @Inject constructor(
    private val authApi: AuthApi,
    private val setUserCredentials: SetUserCredentials,
) : SignUp {

    override suspend fun execute(email: String, password: String) {
        return authApi.signUp(email, password).let {
            setUserCredentials.execute(
                token = it.accessToken,
                refresh = it.refreshToken,
                expiresInMinutes = it.expiresIn
            )
        }
    }
}
