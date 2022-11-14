package com.kelvinfocus.secondpage.hiltmodule

import android.app.Activity
import com.kelvinfocus.secondpage.auth.AuthServiceHelper
import com.kelvinfocus.secondpage.auth.AuthState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @ActivityScoped
    @Provides
    fun providesAuthServiceHelper(
        activity: Activity,
        authState: AuthState
    ) = AuthServiceHelper(activity, authState)
}