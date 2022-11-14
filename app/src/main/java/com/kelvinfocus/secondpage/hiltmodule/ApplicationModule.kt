package com.kelvinfocus.secondpage.hiltmodule

import android.app.Activity
import android.content.Context
import com.kelvinfocus.secondpage.auth.AuthServiceHelper
import com.kelvinfocus.secondpage.auth.AuthState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun providesAuthState() = AuthState()

//    @Singleton
//    @Provides
//    fun providesAuthServiceHelper(
//        @ApplicationContext activity: Context
//    ) = AuthServiceHelper(activity)
}