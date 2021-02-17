package dev.mcd.untitledcaloriesapp.domain.prefs.interactor

import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken

interface GetAccessToken {
    suspend fun execute(): AccessToken?
}
