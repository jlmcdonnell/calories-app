package dev.mcd.untitledcaloriesapp.data.calories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApi
import dev.mcd.untitledcaloriesapp.data.calories.api.CaloriesApiImpl
import dev.mcd.untitledcaloriesapp.data.common.di.Authenticated
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CaloriesModule {

    @Provides
    fun caloriesApi(@Authenticated retrofit: Retrofit): CaloriesApi {
        return CaloriesApiImpl(retrofit)
    }
}
