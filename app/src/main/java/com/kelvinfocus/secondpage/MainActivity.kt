package com.kelvinfocus.secondpage

import android.accounts.AuthenticatorException
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kelvinfocus.secondpage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationException.GeneralErrors
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import retrofit2.http.Url
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject lateinit var authServiceHelper: AuthServiceHelper

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        viewModel.processActivityResultsForLogin(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_feed, R.id.navigation_search, R.id.navigation_subreddit
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun login() {
        val serviceConfig = authServiceHelper.generateRedditAuthServiceConfig()
        val authReq = authServiceHelper.generateAuthRequest(serviceConfig)

        val authService = AuthorizationService(this)
        val intent = authService.getAuthorizationRequestIntent(authReq)
        this.startForResult.launch(intent)
    }
}