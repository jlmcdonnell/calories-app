package dev.mcd.untitledcaloriesapp.data.prefs.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStore
import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStoreImpl

@Module
@InstallIn(SingletonComponent::class)
class PrefsModule {

    @Provides
    fun preferencesStore(@ApplicationContext context: Context): UserPreferencesStore = UserPreferencesStoreImpl(context)
}
