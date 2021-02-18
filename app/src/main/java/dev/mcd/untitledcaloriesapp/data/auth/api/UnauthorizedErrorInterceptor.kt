package dev.mcd.untitledcaloriesapp.data.auth.api

import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class UnauthorizedErrorInterceptor @Inject constructor(
    private val accessTokenStore: AccessTokenStore,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val response = chain.proceed(chain.request())
        if (response.code() == 401) {
            Timber.d("401 received; clearing access token")
            accessTokenStore.clear()
        }
        return@runBlocking response
    }
}
