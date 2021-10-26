package com.mahmoudmohamaddarwish.animatedproductions;

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.Service
import com.mahmoudmohamaddarwish.animatedproductions.data.tmdb.api.getImageUrl
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


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure movies are returned by the service`() = runBlocking {
        val movies = service.getMovies()

        movies.discoverMovieItemDtos.forEach {
            println("${it.title} - ${getImageUrl(it.posterPath)}")
        }

        assert(movies.discoverMovieItemDtos.isNotEmpty())
        assert(movies.discoverMovieItemDtos.all { it.title.isNotBlank() })
    }


    @Suppress("IllegalIdentifier")
    @Test
    fun `ensure shows are returned by the service`() = runBlocking {
        val movies = service.getShows()

        movies.discoverTVItemDtos.forEach {
            println("${it.name} - ${getImageUrl(it.posterPath)}")
        }

        assert(movies.discoverTVItemDtos.isNotEmpty())
        assert(movies.discoverTVItemDtos.all { it.name.isNotBlank() })
    }
}