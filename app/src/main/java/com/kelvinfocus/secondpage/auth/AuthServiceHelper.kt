package com.kelvinfocus.secondpage.auth

import android.content.Context
import android.net.Uri
import com.kelvinfocus.secondpage.Endpoints.REDDIT_AUTH_FULL_ENDPOINT
import com.kelvinfocus.secondpage.Endpoints.REDDIT_TOKEN_FULL_ENDPOINT
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

// Provided by [ActivityModule]
@ActivityScoped
class AuthServiceHelper @Inject constructor (
    @ActivityContext val activity: Context,
    val authState: AuthState
) {

    val authService:AuthorizationService by lazy { AuthorizationService(activity) }

    fun generateRedditAuthServiceConfig(): AuthorizationServiceConfiguration {
        val redditAuthUri = Uri.parse(REDDIT_AUTH_FULL_ENDPOINT)
        val redditTokenEndpointUrl = Uri.parse(REDDIT_TOKEN_FULL_ENDPOINT)

        return AuthorizationServiceConfiguration(redditAuthUri, redditTokenEndpointUrl)
    }
    fun generateAuthRequest(
        serviceConfiguration: AuthorizationServiceConfiguration,
        duration: String = "permanent",
        scopes: List<String> = listOf("read", "vote", "subscribe", "save")
    ) : AuthorizationRequest {
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(REDIRECT_URL)
        )
            .setState(CLIENT_STATE)
            .setNonce(null)
            .setCodeVerifier(null)
            .setScopes(scopes)
            .setAdditionalParameters(mapOf(
                Pair("duration", duration)
            )).build()
    }

    companion object {
//        const val REDDIT_AUTH_ENDPOINT = "https://www.reddit.com/api/v1/authorize"
//        const val REDDIT_TOKEN_ENDPOINT = "https://www.reddit.com/api/v1/access_token"

        const val CLIENT_ID = "-YTeAzstBXbNIzrXHaw-rg"
        const val REDIRECT_URL = "https://chan-kelv.github.io"
        const val CLIENT_STATE = "myTotallyRandomClientState123"

        fun authStateIsValid(responseAuthState: String?): Boolean {
            return responseAuthState == CLIENT_STATE
        }
    }
}