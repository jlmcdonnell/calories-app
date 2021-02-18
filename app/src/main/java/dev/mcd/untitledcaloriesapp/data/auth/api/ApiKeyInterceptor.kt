package dev.mcd.untitledcaloriesapp.data.auth.api

import dev.mcd.untitledcaloriesapp.data.env.Environment
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(
    private val environment: Environment,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        chain.request().newBuilder()
            .addHeader("apiKey", environment.supabaseApiKey)
            .build()
            .let(chain::proceed)
    }
}
