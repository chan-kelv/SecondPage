package com.kelvinfocus.secondpage.ui.feedTab

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.secondpage.AuthServiceHelper
import com.kelvinfocus.secondpage.MainActivity
import com.kelvinfocus.secondpage.databinding.FragmentFeedTabBinding
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import timber.log.Timber
import java.net.URLEncoder
import javax.inject.Inject

@AndroidEntryPoint
class FeedTabFragment : Fragment() {

    private var _binding: FragmentFeedTabBinding? = null
    private val homeViewModel: FeedTabViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedTabBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val loginButton = binding.textHome
        loginButton.text = "Login"
        loginButton.setOnClickListener {
            (activity as? MainActivity)?.login()
//            login()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun login() {
//        val authServiceHelper = authServiceHelperFactory.create()
//
//        val serviceConfig = authServiceHelper.generateRedditAuthServiceConfig()
//        val authReq =authServiceHelper.generateAuthRequest(serviceConfig)
//
//        val authService = AuthorizationService(requireActivity())
//        val intent = authService.getAuthorizationRequestIntent(authReq)
//        (requireActivity() as MainActivity).startForResult.launch(intent)
//    }
}