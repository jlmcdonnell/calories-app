package dev.mcd.untitledcaloriesapp.data.common.time.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.common.time.DateTimeProviderImpl
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider

@Module
@InstallIn(SingletonComponent::class)
class TimeModule {

    @Provides
    fun timeProvider(): DateTimeProvider = DateTimeProviderImpl()
}
