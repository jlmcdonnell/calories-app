package dev.mcd.untitledcaloriesapp.data.common.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.api.ApiKeyInterceptor
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthTokenInterceptor
import dev.mcd.untitledcaloriesapp.data.auth.api.UnauthorizedErrorInterceptor
import dev.mcd.untitledcaloriesapp.data.env.Environment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Authenticated
    fun authenticatedOkClient(
        authInterceptor: AuthTokenInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
        unauthorizedErrorInterceptor: UnauthorizedErrorInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(authInterceptor)
            .addNetworkInterceptor(apiKeyInterceptor)
            .addInterceptor(unauthorizedErrorInterceptor)
            .build()
    }

    @Provides
    @Unauthenticated
    fun unauthenticatedOkClient(
        apiKeyInterceptor: ApiKeyInterceptor,
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
