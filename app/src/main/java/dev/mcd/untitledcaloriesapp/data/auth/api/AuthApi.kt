package dev.mcd.untitledcaloriesapp.data.auth.api

import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.AuthResponse
import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.LoginRequest
import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.SignUpRequest
import dev.mcd.untitledcaloriesapp.data.common.extensions.asException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    fun signUp(email: String, password: String): AuthResponse
    fun login(email: String, password: String): AuthResponse
    fun refreshToken(refreshToken: String): AuthResponse
}

class AuthApiImpl(retrofit: Retrofit) : AuthApi {

    interface API {
        @POST("/auth/v1/signup")
        fun signUp(@Body signUpRequest: SignUpRequest): Call<AuthResponse>

        @POST("/auth/v1/token?grant_type=password")
        fun login(@Body loginRequest: LoginRequest): Call<AuthResponse>

        @POST("/auth/v1/token?grant_type=refresh_token")
        fun refreshToken(@Body refreshToken: Map<String, String>): Call<AuthResponse>
    }

    private val api: API = retrofit.create(API::class.java)

    override fun signUp(email: String, password: String): AuthResponse {
        val body = SignUpRequest(email, password)
        val response = api.signUp(body).execute()
        return handleAuthRequest(response)
    }

    override fun login(email: String, password: String): AuthResponse {
        val body = LoginRequest(email, password)
        val response = api.login(body).execute()
        return handleAuthRequest(response)
    }

    override fun refreshToken(refreshToken: String): AuthResponse {
        val response = api.refreshToken(mapOf("refresh_token" to refreshToken)).execute()
        return handleAuthRequest(response)
    }

    private fun handleAuthRequest(response: Response<AuthResponse>): AuthResponse {
        return if (response.isSuccessful) {
            val loginResponse = response.body()!!
            AuthResponse(
                accessToken = loginResponse.accessToken,
                refreshToken = loginResponse.refreshToken,
                expiresIn = loginResponse.expiresIn,
            )
        } else throw response.asException()
    }
}
