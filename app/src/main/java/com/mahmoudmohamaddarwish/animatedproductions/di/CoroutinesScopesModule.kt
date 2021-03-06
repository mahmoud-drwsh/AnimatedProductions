package com.mahmoudmohamaddarwish.animatedproductions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * This code in its entirety is taken from a medium article by one of the Android development
 * team from google.
 * This is the link:
 * https://medium.com/androiddevelopers/create-an-application-coroutinescope-using-hilt-dd444e721528
 * */
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @ApplicationScope
    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}
