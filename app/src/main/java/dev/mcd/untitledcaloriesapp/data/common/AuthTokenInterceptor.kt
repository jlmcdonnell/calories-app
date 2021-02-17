package dev.mcd.untitledcaloriesapp.data.common

import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetAccessToken
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserCredentials
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AuthTokenInterceptor(
    private val getAccessToken: GetAccessToken,
    private val setUserCredentials: SetUserCredentials,
    private val authApi: AuthApi,
) : Interceptor {

    companion object {
        private const val REFRESH_THRESHOLD_MINUTES = 15L
    }

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        var currentToken = getAccessToken.execute() ?: run {
            Timber.w("No access token")
            return@runBlocking chain.proceed(chain.request())
        }

        if (shouldRefreshToken(currentToken)) {
            currentToken = runCatching {
                refreshToken(currentToken)
            }.onFailure {
                Timber.e(it, "Unable to refresh token")
                // TODO: Log user out
            }.getOrThrow()
        }

        return@runBlocking chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${currentToken.token}")
            .build()
            .let(chain::proceed)
    }

    private fun shouldRefreshToken(accessToken: AccessToken): Boolean {
        val expiry = accessToken.expiryDate.time
        val refreshAt = expiry - TimeUnit.MINUTES.toMillis(REFRESH_THRESHOLD_MINUTES)
        return System.currentTimeMillis() > refreshAt
    }

    private suspend fun refreshToken(accessToken: AccessToken): AccessToken {
        return authApi.refreshToken(accessToken.refresh).let { newToken ->
            setUserCredentials.execute(
                token = newToken.accessToken,
                refresh = newToken.refreshToken,
                expiresInMinutes = newToken.expiresIn,
            )
            getAccessToken.execute()!!
        }
    }
}
