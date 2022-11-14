package com.kelvinfocus.secondpage.auth

import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

// Provided [ApplicationModule]
@Singleton
class AuthState() {
    fun updateAuthToken(authToken: TokenResponse) {

    }

    // TODO CRUD
}