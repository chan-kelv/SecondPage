package com.kelvinfocus.secondpage

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvinfocus.secondpage.auth.AuthRepository
import com.kelvinfocus.secondpage.auth.AuthServiceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationResponse
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val authRepository: AuthRepository
): ViewModel() {
    private val _authError = MutableLiveData<String>()
    val authError: LiveData<String> = _authError
    fun updateAuthError(errorMessage: String) { _authError.value = errorMessage }

    fun processActivityResultsForLogin(result: ActivityResult) {
        viewModelScope.launch {
            validateAuthResponse(result)?.let { authCode ->
                Timber.d("auth code: $authCode")
                withContext(Dispatchers.IO) {
                    val tokenResponse = authRepository.getAuthToken(authCode)
                    Timber.d(tokenResponse.body().toString())
                }
            }
        }
//        Timber.d("from activity: ${result.data?.data}")
//        if (result.resultCode == Activity.RESULT_OK) {
//            result.data?.let { intent ->
//                val loginCode = validateAuthResponse(intent.data)
//                if (!loginCode.isNullOrBlank()) {
//                    Timber.d("Login success! Code: $loginCode")
//                } // else validator already updated error state, just return
//            }
//        } else {
//            Timber.e("Auth response not ok")
//            updateAuthError("Auth response empty")
//        }
    }

    fun requestAuthToken(authServiceHelper: AuthServiceHelper, validResponse: AuthorizationResponse) {
        val tokenRequest = validResponse.createTokenExchangeRequest()
        authServiceHelper.authService.performTokenRequest(tokenRequest) { response, ex ->
            if (response != null) {
                Timber.d("Auth token: ${response.accessToken}")
            } else {
                updateAuthError("Token response fail")
                Timber.e(ex)
            }
        }
    }

    private fun validateAuthResponse(result: ActivityResult): String? {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val redditAuthError = uri.getQueryParameter("error")
                val state = uri.getQueryParameter("state")
                val code = uri.getQueryParameter("code")

                if (redditAuthError?.isNotBlank() == true) {
                    updateAuthError(redditAuthError)
                } else if (!AuthServiceHelper.authStateIsValid(state)) {
                    updateAuthError("Auth State invalid!")
                } else if (code.isNullOrBlank()) {
                    updateAuthError("Auth code empty")
                } else {
                    return code
                }
            } ?: run {
                updateAuthError("Reddit response empty")
            }
        } else {
            Timber.e("Auth response not ok")
            updateAuthError("Auth response empty")
        }
        return null
    }
}