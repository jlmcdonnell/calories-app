package dev.mcd.untitledcaloriesapp.domain.auth.interactor

interface IsAuthenticated {

    enum class Result {
        Authenticated,
        NotAuthenticated,
    }

    suspend fun execute(): Result
}
