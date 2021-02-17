package dev.mcd.untitledcaloriesapp.data.auth.interactor

import dev.mcd.untitledcaloriesapp.domain.auth.interactor.IsAuthenticated
import dev.mcd.untitledcaloriesapp.domain.prefs.interactor.GetUserPrefs
import javax.inject.Inject

class IsAuthenticatedImpl @Inject constructor(private val getUserPrefs: GetUserPrefs) : IsAuthenticated {
    override suspend fun execute(): IsAuthenticated.Result {
        val isAuthenticated = getUserPrefs.execute().let {
            !it.userToken.isNullOrBlank()
        }
        return if (isAuthenticated) {
            IsAuthenticated.Result.Authenticated
        } else {
            IsAuthenticated.Result.NotAuthenticated
        }
    }
}
