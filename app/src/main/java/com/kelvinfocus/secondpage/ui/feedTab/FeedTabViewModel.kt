package com.kelvinfocus.secondpage.ui.feedTab

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelvinfocus.secondpage.AuthServiceHelper
import com.kelvinfocus.secondpage.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@HiltViewModel
class FeedTabViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}