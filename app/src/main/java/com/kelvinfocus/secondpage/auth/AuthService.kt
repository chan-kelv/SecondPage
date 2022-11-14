package com.kelvinfocus.secondpage.auth

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST(AuthRepository.REDDIT_ACCESS_TOKEN_ENDPOINT)
    @FormUrlEncoded
    suspend fun authToken(
        @Field("code") code: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("redirect_uri") redirectUri: String = AuthServiceHelper.REDIRECT_URL,
        @Header("Authorization") authorization: String
//        @Body tokenRequest: TokenRequest
    ): Response<AuthRepository.TokenResponse>
}