package dev.mcd.untitledcaloriesapp.data.auth.api

import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val accessTokenStore: AccessTokenStore,
    private val authApi: AuthApi,
    private val dateTimeProvider: DateTimeProvider,
) : Interceptor {

    companion object {
        private const val REFRESH_THRESHOLD_MINUTES = 15L
    }

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        var currentToken = accessTokenStore.get() ?: run {
            Timber.w("No access token")
            return@runBlocking chain.proceed(chain.request())
        }

        if (shouldRefreshToken(currentToken)) {
            currentToken = runCatching {
                refreshToken(currentToken)
            }.onFailure {
                Timber.e(it, "Unable to refresh token")
                accessTokenStore.clear()
            }.getOrThrow()
        }

        return@runBlocking chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${currentToken.accessToken}")
            .build()
            .let(chain::proceed)
    }

    private fun shouldRefreshToken(accessToken: AccessToken): Boolean {
        val refreshAt = accessToken.tokenExpiry - TimeUnit.MINUTES.toMillis(REFRESH_THRESHOLD_MINUTES)
        return dateTimeProvider.now > refreshAt
    }

    private suspend fun refreshToken(accessToken: AccessToken): AccessToken {
        return authApi.refreshToken(accessToken.refreshToken).also {
            accessTokenStore.set(it)
        }
    }
}
