package com.kelvinfocus.secondpage.auth

import android.util.Base64
import com.google.gson.annotations.SerializedName
import com.kelvinfocus.secondpage.ApiClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    interface AuthService {
        @POST(REDDIT_ACCESS_TOKEN_ENDPOINT)
        @FormUrlEncoded
//        @Headers("Accept: application/json")
        suspend fun authToken(
            @Field("code") code: String,
            @Field("grant_type") grantType: String = "authorization_code",
            @Field("redirect_uri") redirectUri: String = AuthServiceHelper.REDIRECT_URL,
            @Header("Authorization") authorization: String
//        @Body tokenRequest: TokenRequest
        ): Response<TokenResponse>
    }

    data class TokenRequest(
        val code: String
    ) {
        @SerializedName("grant_type")
        val grantType: String = "authorization_code"

        @SerializedName("redirect_uri")
        val redirectUri: String = AuthServiceHelper.REDIRECT_URL

        fun generateAuth(): String {
            val clientId = AuthServiceHelper.CLIENT_ID
            val clientSecret = "" // there is none for mobile

            val credentials = "$clientId:$clientSecret".toByteArray()
            val basicAuth = "Basic " + Base64.encodeToString(credentials, Base64.NO_WRAP)
            Timber.d("BasicAuth: $basicAuth")
            return basicAuth
        }
    }

    data class TokenResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String,
        @SerializedName("expires_in") val expiresIn: String,
        @SerializedName("refresh_token") val refreshToken: String,
        val scope: String
    )

    private val instance: AuthRepository.AuthService by lazy {
        ApiClient().retrofitAuthClient.create(AuthRepository.AuthService::class.java)
    }

    suspend fun getToken(authCode: String): Response<TokenResponse> {
        val tokenRequest = TokenRequest(authCode)
        return instance.authToken(authCode, authorization = tokenRequest.generateAuth())
    }

    companion object {
        const val REDDIT_BASE_URL = "https://www.reddit.com/api/v1/"

        const val REDDIT_AUTH_ENDPOINT = "${REDDIT_BASE_URL}authorize"
        const val REDDIT_TOKEN_ENDPOINT = "${REDDIT_BASE_URL}access_token"
        const val REDDIT_ACCESS_TOKEN_ENDPOINT = "access_token"
    }
}