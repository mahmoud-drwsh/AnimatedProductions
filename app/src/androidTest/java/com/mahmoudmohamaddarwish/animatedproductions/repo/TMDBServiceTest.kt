package com.mahmoudmohamaddarwish.animatedproductions.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TMDBServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var service: Service

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun ensureNonEmptyMoviesListIsReturnedByTheService() = runBlocking {
        val movies = service.getMovies()

        assert(movies.discoverMovieItemDtos.isNotEmpty())
    }


    @Test
    fun ensureNonEmptyShowsListIsReturnedByTheService() = runBlocking {
        val movies = service.getShows()

        assert(movies.discoverTVItemDtos.isNotEmpty())
    }
}

private const val TAG = "TMDBServiceTest"