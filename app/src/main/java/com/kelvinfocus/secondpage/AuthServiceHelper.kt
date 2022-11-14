package com.kelvinfocus.secondpage

import android.app.Activity
import android.net.Uri
import dagger.hilt.android.qualifiers.ActivityContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

class AuthServiceHelper constructor (
    @ActivityContext private val activity: Activity) {
    class AuthServiceHelperFactory @Inject constructor(
        private val activity: Activity
    ) {
        fun create(): AuthServiceHelper {
            return AuthServiceHelper(activity)
        }
    }

    companion object {
        const val REDDIT_AUTH_ENDPOINT = "https://www.reddit.com/api/v1/authorize"
        const val REDDIT_TOKEN_ENDPOINT = "https://www.reddit.com/api/v1/access_token"

        const val CLIENT_ID = "-YTeAzstBXbNIzrXHaw-rg"
        const val REDIRECT_URL = "https://chan-kelv.github.io"
        const val CLIENT_STATE = "myTotallyRandomClientState123"
    }
    fun generateRedditAuthServiceConfig(): AuthorizationServiceConfiguration {
        val redditAuthUri = Uri.parse(REDDIT_AUTH_ENDPOINT)
        val redditTokenEndpointUrl = Uri.parse(REDDIT_TOKEN_ENDPOINT)

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
}