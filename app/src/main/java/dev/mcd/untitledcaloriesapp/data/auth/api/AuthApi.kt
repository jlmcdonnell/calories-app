package dev.mcd.untitledcaloriesapp.data.auth.api

import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.AuthResponse
import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.LoginRequest
import dev.mcd.untitledcaloriesapp.data.auth.api.serializer.SignUpRequest
import dev.mcd.untitledcaloriesapp.data.common.extensions.asException
import dev.mcd.untitledcaloriesapp.domain.auth.entity.AccessToken
import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface AuthApi {
    fun signUp(email: String, password: String): AccessToken
    fun login(email: String, password: String): AccessToken
    fun refreshToken(refreshToken: String): AccessToken
}

class AuthApiImpl(
    retrofit: Retrofit,
    private val dateTimeProvider: DateTimeProvider,
) : AuthApi {

    interface API {
        @POST("/auth/v1/signup")
        fun signUp(@Body signUpRequest: SignUpRequest): Call<AuthResponse>

        @POST("/auth/v1/token?grant_type=password")
        fun login(@Body loginRequest: LoginRequest): Call<AuthResponse>

        @POST("/auth/v1/token?grant_type=refresh_token")
        fun refreshToken(@Body refreshToken: Map<String, String>): Call<AuthResponse>
    }

    private val api: API = retrofit.create(API::class.java)

    override fun signUp(email: String, password: String): AccessToken {
        val body = SignUpRequest(email, password)
        val response = api.signUp(body).execute()
        return handleAuthRequest(response)
    }

    override fun login(email: String, password: String): AccessToken {
        val body = LoginRequest(email, password)
        val response = api.login(body).execute()
        return handleAuthRequest(response)
    }

    override fun refreshToken(refreshToken: String): AccessToken {
        val response = api.refreshToken(mapOf("refresh_token" to refreshToken)).execute()
        return handleAuthRequest(response)
    }

    private fun handleAuthRequest(response: Response<AuthResponse>): AccessToken {
        return if (response.isSuccessful) {
            val authResponse = response.body()!!
            val tokenTtl = TimeUnit.MINUTES.toMillis(authResponse.expiresInMinutes)
            val tokenExpiry = dateTimeProvider.now + tokenTtl

            AccessToken(
                accessToken = authResponse.accessToken,
                refreshToken = authResponse.refreshToken,
                tokenExpiry = tokenExpiry,
            )
        } else throw response.asException()
    }
}
