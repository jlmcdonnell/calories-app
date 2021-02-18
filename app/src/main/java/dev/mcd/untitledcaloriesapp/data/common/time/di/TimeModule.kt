package dev.mcd.untitledcaloriesapp.data.common.time.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.common.time.SystemTimeProvider
import dev.mcd.untitledcaloriesapp.domain.common.time.TimeProvider

@Module
@InstallIn(SingletonComponent::class)
class TimeModule {

    @Provides
    fun timeProvider(): TimeProvider = SystemTimeProvider()
}
