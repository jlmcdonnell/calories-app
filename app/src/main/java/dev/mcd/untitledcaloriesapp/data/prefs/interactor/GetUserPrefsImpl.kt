package dev.mcd.untitledcaloriesapp.data.prefs.interactor

import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStore
import dev.mcd.untitledcaloriesapp.domain.prefs.entity.UserPreferences
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetUserPrefs
import javax.inject.Inject

class GetUserPrefsImpl @Inject constructor(private val prefsStore: UserPreferencesStore) : GetUserPrefs {
    override suspend fun execute(): UserPreferences {
        return prefsStore.get()
    }
}
