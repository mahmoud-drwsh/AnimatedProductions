package com.mahmoudmohamaddarwish.animatedproductions.di

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.mahmoudmohamaddarwish.animatedproductions.data.datastore.dataStore
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.FavoritesListRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.ListMoviesAndShowsRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.NightModeRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.room.FavoritesDao
import com.mahmoudmohamaddarwish.animatedproductions.data.room.FavoritesDb
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Constants
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.okHttpClient
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.NightModeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This module is used for providing instances of the desired classes throughout the app
 * */
@InstallIn(SingletonComponent::class)
@Module
object AppHiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoritesDb =
        Room.databaseBuilder(context, FavoritesDb::class.java, "test")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideFavoritesDao(favoritesDb: FavoritesDb): FavoritesDao =
        favoritesDb.favoritesDao()


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubApi(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Singleton
    @Provides
    fun provideListMoviesAndShowsUseCase(listMoviesAndShowsRepo: ListMoviesAndShowsRepo): ListMoviesAndShowsUseCase =
        listMoviesAndShowsRepo

    @Singleton
    @Provides
    fun provideFavoritesListUseCase(favoritesListRepo: FavoritesListRepo): FavoritesListUseCase =
        favoritesListRepo

    @Singleton
    @Provides
    fun provideNightModeUseCase(nightModeRepo: NightModeRepo): NightModeUseCase = nightModeRepo

    @Singleton
    @Provides
    fun provideAppResources(@ApplicationContext context: Context): Resources = context.resources
}


