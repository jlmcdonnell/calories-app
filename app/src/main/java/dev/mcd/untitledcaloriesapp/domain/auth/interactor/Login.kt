package dev.mcd.untitledcaloriesapp.domain.auth.interactor

interface Login {
    suspend fun execute(email: String, password: String)
}
