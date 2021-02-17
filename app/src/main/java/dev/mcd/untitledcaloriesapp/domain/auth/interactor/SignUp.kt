package dev.mcd.untitledcaloriesapp.domain.auth.interactor

interface SignUp {
    suspend fun execute(email: String, password: String)
}
