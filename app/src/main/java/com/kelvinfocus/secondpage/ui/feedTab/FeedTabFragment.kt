package com.kelvinfocus.secondpage.ui.feedTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kelvinfocus.secondpage.MainActivity
import com.kelvinfocus.secondpage.databinding.FragmentFeedTabBinding
import dagger.hilt.android.AndroidEntryPoint

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