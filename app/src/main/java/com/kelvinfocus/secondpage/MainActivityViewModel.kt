package com.kelvinfocus.secondpage

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.openid.appauth.AuthorizationResponse
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {
    private val _authError = MutableLiveData<String>()
    val authError: LiveData<String> = _authError
    fun updateAuthError(errorMessage: String) { _authError.value = errorMessage }

    fun processActivityResultsForLogin(result: ActivityResult) {
        Timber.d("from activity: ${result.data?.data}")
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val resp = AuthorizationResponse.fromIntent(intent)
                val exception = AuthorizationResponse.fromIntent(intent)

                val loginCode = validateAuthResponse(intent.data, resp, exception)
                if (!loginCode.isNullOrBlank()) {
                    Timber.d("Login success! Code: $loginCode")
                } // else validator already updated error state, just return
            }
        } else {
            Timber.e("Auth response not ok")
            updateAuthError("Auth response empty")
        }
    }

    private fun validateAuthResponse(
        redditAuthResponseUri: Uri?,
        resp: AuthorizationResponse?,
        exception: AuthorizationResponse?
    ): String? {
        redditAuthResponseUri?.let { uri ->
            val redditAuthError = uri.getQueryParameter("error")
            val state = uri.getQueryParameter("state")
            val code = uri.getQueryParameter("code")

            if (redditAuthError?.isNotBlank() == true) {
                updateAuthError(redditAuthError)
            } else if (!AuthServiceHelper.authStateIsValid(state)) {
                updateAuthError("Auth State invalid!")
            } else if (resp == null && exception != null) {
                updateAuthError(exception.toString())
            } else if (code.isNullOrBlank()) {
                updateAuthError("Auth code empty")
            } else {
                return code
            }
        } ?: run {
            updateAuthError("Reddit response empty")
        }
        return null
    }
}