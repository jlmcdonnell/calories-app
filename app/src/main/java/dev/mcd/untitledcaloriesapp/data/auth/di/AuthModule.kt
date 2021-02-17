package dev.mcd.untitledcaloriesapp.data.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApi
import dev.mcd.untitledcaloriesapp.data.auth.api.AuthApiImpl
import dev.mcd.untitledcaloriesapp.data.common.di.Unauthenticated
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun authApi(@Unauthenticated retrofit: Retrofit): AuthApi {
        return AuthApiImpl(retrofit)
    }
}
