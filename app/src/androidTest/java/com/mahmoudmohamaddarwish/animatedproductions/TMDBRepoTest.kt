package com.mahmoudmohamaddarwish.animatedproductions;

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.repos.ListMoviesAndShowsRepo
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getImageUrl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
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
class TMDBRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var listMoviesAndShowsRepo: ListMoviesAndShowsRepo

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure movies are returned by the service`() = runBlocking {
        val moviesFlow = listMoviesAndShowsRepo.moviesFlow

        val resource = moviesFlow.take(1).first()

        assert(resource == Resource.Loading)

        assert(moviesFlow.drop(1).first() is Resource.Success)
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure shows are returned by the service`() = runBlocking {
        val showsFlow = listMoviesAndShowsRepo.showsFlow

        val movies = showsFlow.take(1).first()

        assert(movies == Resource.Loading)

        assert(showsFlow.drop(1).first() is Resource.Success)
    }
}