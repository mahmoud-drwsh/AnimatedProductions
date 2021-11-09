package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FavoritesRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoritesUseCase: FavoritesListUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun favoritesSuccessfullyAdded(): Unit = runBlocking {
        favoritesUseCase.deleteAll()

        favoritesUseCase.addDummyFavorites()

        favoritesUseCase.moviesFlow.first().contains(Production.movieDummy)

        favoritesUseCase.showsFlow.first().contains(Production.showDummy)
    }

    companion object {
        fun FavoritesListUseCase.addDummyFavorites() = runBlocking {
            addFavorite(Production.movieDummy)
            addFavorite(Production.showDummy)
        }
    }
}