package com.kelvinfocus.secondpage.ui.feedTab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.secondpage.databinding.FragmentFeedTabBinding
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import timber.log.Timber
import java.net.URLEncoder

class FeedTabFragment : Fragment() {

    private var _binding: FragmentFeedTabBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(FeedTabViewModel::class.java)

        _binding = FragmentFeedTabBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val loginButton = binding.textHome
        loginButton.text = "Login"
        loginButton.setOnClickListener {
            login()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login() {
        val redditAuthUrl = "https://www.reddit.com/api/v1/authorize"
        val redditAuthUri = Uri.parse(redditAuthUrl)
        val redditTokenEndpoint = Uri.parse("https://www.reddit.com/api/v1/access_token") // i think??
        val serviceConfig = AuthorizationServiceConfiguration(redditAuthUri, redditTokenEndpoint)

//        val redirectUrl = Uri.parse(URLEncoder.encode("http://com.kelvinfocus.secondpage/auth"))

        val authReq =generateAuthRequest(serviceConfig)

        val authService = AuthorizationService(requireActivity())
        val intent = authService.getAuthorizationRequestIntent(authReq)
        requireActivity().startActivityForResult(intent, 124526)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 124526) {
            Timber.d("$data")
        }
    }
//
    val clientId:String = "-YTeAzstBXbNIzrXHaw-rg"
    val redirectUrl = "https://chan-kelv.github.io/SecondPage"
    val duration = "permanent"
    val state = "myTotallyRandomString123"
//    private fun generateRedditAuthUri(): String {
//        val clientId = "-YTeAzstBXbNIzrXHaw-rg"
//        val code = "code"
//        val state = "myTotallyRandomString123"
//        val redirectUrl = URLEncoder.encode("http://com.kelvinfocus.secondpage/auth")
//        val duration = "permanent"
//
//        val scopes = listOf("read", "vote", "subscribe", "save")
//        val scopeBuffer = StringBuffer()
//        scopes.forEach { scopeBuffer.append(it).append("+") }
//        val scopeUri = scopeBuffer.toString().removeSuffix("+")
//
//        return "https://www.reddit.com/api/v1/authorize" +
////                "?client_id=$clientId" +
////                "&response_type=$code" +
//                "&state=$state" +
////                "&redirect_uri=$redirectUrl" +
//                "&duration=$duration" +
//                "&scope=$scopeUri"
//    }

    fun generateAuthRequest(serviceConfiguration: AuthorizationServiceConfiguration) : AuthorizationRequest {
        val duration = "permanent"

        val scopes = listOf("read", "vote", "subscribe", "save")
        val scopeBuffer = StringBuffer()
        scopes.forEach { scopeBuffer.append(it).append("+") }
        val scopeUri = scopeBuffer.toString().removeSuffix("+")
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            clientId,
            ResponseTypeValues.CODE,
            Uri.parse(redirectUrl)
        )
            .setState(state)
            .setNonce(null)
            .setCodeVerifier(null)
            .setScopes(scopes)
            .setAdditionalParameters(mapOf(
                Pair("duration", "permanent")
            )).build()
    }
}