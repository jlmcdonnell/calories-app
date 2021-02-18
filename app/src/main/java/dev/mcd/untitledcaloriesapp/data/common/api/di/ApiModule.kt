package dev.mcd.untitledcaloriesapp.data.common.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.data.common.AuthTokenInterceptor
import dev.mcd.untitledcaloriesapp.data.env.Environment
import dev.mcd.untitledcaloriesapp.domain.common.time.TimeProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @AuthInterceptor
    fun authInterceptor(accessTokenStore: AccessTokenStore, authApi: AuthApi, timeProvider: TimeProvider): Interceptor {
        return AuthTokenInterceptor(accessTokenStore, authApi, timeProvider)
    }

    @Provides
    @ApiKeyInterceptor
    fun apiKeyInterceptor(environment: Environment): Interceptor {
        return Interceptor { chain ->
            chain.request().newBuilder()
                .addHeader("apiKey", environment.supabaseApiKey)
                .build()
                .let(chain::proceed)
        }
    }

    @Provides
    @Authenticated
    fun authenticatedOkClient(
        @AuthInterceptor authInterceptor: Interceptor,
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(authInterceptor)
            .addNetworkInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Unauthenticated
    fun unauthenticatedOkClient(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Authenticated
    fun authenticatedRetrofit(
        environment: Environment,
        @Authenticated okClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(environment.supabaseUrl)
            .client(okClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Unauthenticated
    fun unauthenticatedRetrofit(
        environment: Environment,
        @Unauthenticated okClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(environment.supabaseUrl)
            .client(okClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
