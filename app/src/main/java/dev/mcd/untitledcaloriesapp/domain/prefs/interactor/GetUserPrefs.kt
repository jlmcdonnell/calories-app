package dev.mcd.untitledcaloriesapp.domain.prefs.interactor

import dev.mcd.untitledcaloriesapp.domain.prefs.entity.UserPreferences

interface GetUserPrefs {
    suspend fun execute(): UserPreferences
}
