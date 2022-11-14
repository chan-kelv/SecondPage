package com.kelvinfocus.secondpage.hiltmodule

import android.app.Activity
import com.kelvinfocus.secondpage.AuthServiceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @ActivityScoped
    @Provides
    fun providesAuthServiceHelper(@ActivityContext activity: Activity) = AuthServiceHelper(activity)
}