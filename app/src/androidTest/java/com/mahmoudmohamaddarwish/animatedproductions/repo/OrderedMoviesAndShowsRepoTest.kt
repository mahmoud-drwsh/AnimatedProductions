package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.OrderedMoviesAndShowsListsUseCase
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
class OrderedMoviesAndShowsRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var listMoviesAndShowsRepo: OrderedMoviesAndShowsListsUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure movies are returned by the service`() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.orderedMoviesFlow

        val successResource = moviesFlow.first { it is Resource.Success }

        assert(successResource is Resource.Success)

        with(successResource as Resource.Success) {
            assert(data.all { it.name.isBlank() })
        }
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure first emitted movies resource object is the Loading one`() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.orderedMoviesFlow

        val resource = moviesFlow.first()

        assert(resource is Resource.Loading)
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure first emitted TV shows resource object is the Loading one`() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.orderedShowsFlow

        val resource = moviesFlow.first()

        assert(resource is Resource.Loading)
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure shows are returned by the service`() = runBlocking {
        val showsFlow = listMoviesAndShowsRepo.orderedShowsFlow

        val movies = showsFlow.first()

        assert(movies is Resource.Loading)

        val value = showsFlow.first { it is Resource.Success } as Resource.Success

        assert(value.data.all { it.name.isNotBlank() })
    }
}