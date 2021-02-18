package dev.mcd.untitledcaloriesapp.domain.auth.interactor

import dev.mcd.untitledcaloriesapp.domain.auth.entity.AuthenticationState

interface GetAuthenticationState {
    suspend fun execute(): AuthenticationState
}
