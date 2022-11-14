package com.kelvinfocus.secondpage.auth

import android.util.Base64
import com.google.gson.annotations.SerializedName
import com.kelvinfocus.secondpage.ApiClient
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    private val authClient: AuthService by lazy {
        ApiClient().retrofitAuthClient.create(AuthService::class.java)
    }

    suspend fun getAuthToken(authCode: String): Response<TokenResponse> {
        val tokenRequest = TokenRequest(authCode)
        return authClient.authToken(authCode, authorization = TokenRequest.generateAuthHeader())
    }

    // TODO not tested yet
    suspend fun refreshAuthToken(refreshToken: String): Response<TokenResponse> {
        val auth = TokenRequest.generateAuthHeader()
        return authClient.refreshToken(auth, refreshToken)
    }

    data class TokenRequest(
        val authCode: String
    ) {
        companion object {
            fun generateAuthHeader(): String {
                val clientId = AuthServiceHelper.CLIENT_ID
                val clientSecret = "" // there is none for mobile

                val credentials = "$clientId:$clientSecret".toByteArray()
                val basicAuth = "Basic " + Base64.encodeToString(credentials, Base64.NO_WRAP)
                Timber.d("BasicAuth: $basicAuth")
                return basicAuth
            }
        }
    }

    data class TokenResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String,
        @SerializedName("expires_in") val expiresIn: String,
        @SerializedName("refresh_token") val refreshToken: String,
        val scope: String
    ) {
        fun generateAuthTokenString(): String {
            var tokenArray = accessToken.toByteArray()
            val authString = "bearer " + Base64.encodeToString(tokenArray, Base64.NO_WRAP)
            return authString
        }
    }
}