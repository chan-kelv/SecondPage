package com.kelvinfocus.secondpage.auth

import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import javax.inject.Inject
import javax.inject.Singleton

// Provided [ApplicationModule]
@Singleton
class AuthState() {
    fun updateAuthState(response: AuthorizationResponse, exception: AuthorizationException) {

    }

    // TODO CRUD
}