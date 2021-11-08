package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OrderedFavoritesListRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoritesListUseCase: FavoritesListUseCase

    private val favoritesFlow
        get() = favoritesListUseCase.favoriteMoviesFlow

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addFavorite() = runBlocking {
        favoritesListUseCase.removeFavorite(Production.movieDummy)

        var successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        assert(successResource.data.contains(Production.movieDummy).not())

        favoritesListUseCase.addFavorite(Production.movieDummy)

        successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        assert(successResource.data.contains(Production.movieDummy))
    }

    @Test
    fun removeFavorite() = runBlocking {
        favoritesListUseCase.addFavorite(Production.movieDummy)

        var successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        assert(successResource.data.contains(Production.movieDummy))

        favoritesListUseCase.removeFavorite(Production.movieDummy)

        successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        assert(successResource.data.contains(Production.movieDummy).not())
    }

    @Test
    fun getMoviesFavorites() = runBlocking {
        favoritesListUseCase.deleteAll()

        favoritesListUseCase.addFavorite(Production.movieDummy)

        val successResource = favoritesListUseCase.favoriteMoviesFlow.first {
            it is Resource.Success
        } as Resource.Success

        assert(successResource.data.size == 1)

        assert(successResource.data.contains(Production.movieDummy))
    }

    @Test
    fun getShowsFavorites() = runBlocking {
        favoritesListUseCase.deleteAll()

        favoritesListUseCase.addFavorite(Production.showDummy)

        val successResource = favoritesListUseCase.favoriteShowsFlow.first {
            it is Resource.Success
        } as Resource.Success

        assert(successResource.data.size == 1)

        assert(successResource.data.contains(Production.showDummy))
    }

    @Test
    fun ensureFirstEmittedResourceValueIsLoading() = runBlocking {
        assert(favoritesFlow.first() is Resource.Loading)
    }

    @Test
    fun ensureSecondEmittedResourceValueIsSuccess() = runBlocking {
        assert(favoritesFlow.take(2).last() is Resource.Success)
    }
}