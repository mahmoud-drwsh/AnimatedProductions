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


    @Test
    fun ensureLoadingStateIsEmittedFirstByTheMoviesFlow() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.moviesFlow

        val firstEmittedValue = moviesFlow.first()

        assert(firstEmittedValue is Resource.Loading)
    }


    @Test
    fun ensureLoadingStateIsEmittedFirstByTheShowsFlow() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.showsFlow

        val firstEmittedValue = moviesFlow.first()

        assert(firstEmittedValue is Resource.Loading)
    }


    @Test
    fun ensureValidMovieObjectsAreReturnedByTheService() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.moviesFlow

        val firstSuccessEmitted = moviesFlow.first { it is Resource.Success } as Resource.Success

        val allMovieObjectsHaveNonBlankNameProperties =
            firstSuccessEmitted.data.all { it.name.isNotBlank() }

        assert(allMovieObjectsHaveNonBlankNameProperties)
    }


    @Test
    fun ensureValidTVShowObjectsAreReturnedByTheService() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.showsFlow

        val firstSuccessEmitted = moviesFlow.first { it is Resource.Success } as Resource.Success

        val allShowObjectsHaveNonBlankNameProperties =
            firstSuccessEmitted.data.all { it.name.isNotBlank() }

        assert(allShowObjectsHaveNonBlankNameProperties)
    }
}