package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
class FavoritesListRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoritesListUseCase: FavoritesListUseCase

    private val favoritesFlow
        get() = favoritesListUseCase.favoritesFlow

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addFavorite() = runBlocking {
        // make sure the dummy data is removed before inserting it
        favoritesListUseCase.removeFavorite(Production.dummy)

        // getting the first success resource emitted
        var successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        // asserting that the database doesn't contain the dummy data
        assert(successResource.data.contains(Production.dummy).not())

        // inserting the dummy data
        favoritesListUseCase.addFavorite(Production.dummy)

        // getting the list of favorites after inserting the dummy data
        successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        // asserting that the data now contains the dummy data
        assert(successResource.data.contains(Production.dummy))
    }

    @Test
    fun removeFavorite() = runBlocking {
        // make sure the dummy data is added before inserting it
        favoritesListUseCase.addFavorite(Production.dummy)

        // getting the first success resource emitted
        var successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        // asserting that the database doesn't contain the dummy data
        assert(successResource.data.contains(Production.dummy))

        // inserting the dummy data
        favoritesListUseCase.removeFavorite(Production.dummy)

        // getting the list of favorites after removing the dummy data
        successResource = favoritesFlow.first { it is Resource.Success } as Resource.Success

        // asserting that the data now doesn't contain the dummy data
        assert(successResource.data.contains(Production.dummy).not())
    }

    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure first emitted resource value is Loading`() = runBlocking {
        assert(favoritesFlow.first() is Resource.Loading)
    }

    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure second emitted resource value is Success`() = runBlocking {
        assert(favoritesFlow.take(2).last() is Resource.Success)
    }
}