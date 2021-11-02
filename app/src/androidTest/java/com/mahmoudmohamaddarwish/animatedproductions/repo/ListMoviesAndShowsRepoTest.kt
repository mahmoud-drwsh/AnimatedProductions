package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.ListMoviesAndShowsUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ListMoviesAndShowsRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var listMoviesAndShowsRepo: ListMoviesAndShowsUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure movies are returned by the service`() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.moviesFlow

        val resource = moviesFlow.first()

        assert(resource is Resource.Loading)

        assert(moviesFlow.dropWhile { it !is Resource.Success }.first() is Resource.Success)
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure shows are returned by the service`() = runBlocking {
        val showsFlow = listMoviesAndShowsRepo.showsFlow

        val movies = showsFlow.first()

        assert(movies is Resource.Loading)

        assert(showsFlow.dropWhile { it !is Resource.Success }.first() is Resource.Success)
    }
}