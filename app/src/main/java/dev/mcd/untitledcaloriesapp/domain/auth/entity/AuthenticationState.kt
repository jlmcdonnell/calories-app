package dev.mcd.untitledcaloriesapp.domain.auth.entity

enum class AuthenticationState {
    Authenticated,
    RefreshRequired,
    NotAuthenticated,
}
