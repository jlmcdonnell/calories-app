package dev.mcd.untitledcaloriesapp.data.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApiImpl
import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStore
import dev.mcd.untitledcaloriesapp.data.auth.store.AccessTokenStoreImpl
import dev.mcd.untitledcaloriesapp.data.common.api.di.Unauthenticated
import dev.mcd.untitledcaloriesapp.domain.common.time.TimeProvider
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun authApi(@Unauthenticated retrofit: Retrofit, timeProvider: TimeProvider): AuthApi {
        return AuthApiImpl(retrofit, timeProvider)
    }

    @Provides
    fun accessTokenStore(@ApplicationContext context: Context): AccessTokenStore {
        return AccessTokenStoreImpl(context)
    }
}
