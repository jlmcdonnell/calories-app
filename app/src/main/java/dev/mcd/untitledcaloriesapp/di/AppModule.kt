package dev.mcd.untitledcaloriesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.env.Environment
import dev.mcd.untitledcaloriesapp.data.env.Production

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun environment(): Environment = Production()
}
