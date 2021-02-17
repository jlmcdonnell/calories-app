package dev.mcd.untitledcaloriesapp.data.prefs.interactor

import dev.mcd.untitledcaloriesapp.data.prefs.store.UserPreferencesStore
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.SetUserPendingConfirmation
import javax.inject.Inject

class SetUserPendingConfirmationImpl @Inject constructor(private val preferencesStore: UserPreferencesStore) : SetUserPendingConfirmation {
    override suspend fun execute() {
        preferencesStore.setPendingConfirmation(true)
    }
}
