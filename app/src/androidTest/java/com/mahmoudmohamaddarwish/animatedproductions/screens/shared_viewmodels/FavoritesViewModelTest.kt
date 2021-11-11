package com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.domain.usecase.FavoritesListUseCase
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FavoritesViewModelTest {


    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()


    private lateinit var favoritesViewModel: FavoritesViewModel

    @Inject
    lateinit var favoritesListUseCase: FavoritesListUseCase


    @Before
    fun setup() {
        hiltRule.inject()

        instantiateViewModel()

        deleteAllFavorites()
    }

    @Test
    fun ensureAFavoriteMovieCanBeAdded(): Unit = runBlocking {
        addTheDummyMovieFavoriteAndEnsureItIsAdded(Production.movieDummy)
    }

    @Test
    fun ensureAFavoriteMovieCanBeRemovedAfterBeingAdded(): Unit = runBlocking {
        val movieDummy = Production.movieDummy

        addTheDummyMovieFavoriteAndEnsureItIsAdded(movieDummy)

        favoritesViewModel.toggleFavoriteStatus(movieDummy, true)

        // wait for the list to be emptied.
        val list = favoritesListUseCase.moviesFlow.first { it.isEmpty() }

        // should be empty because only one item should have been there.
        assert(list.isEmpty())
    }

    /**
     * After this executes successfully, the movie dummy object will be in the favorite list.
     * */
    private suspend fun addTheDummyMovieFavoriteAndEnsureItIsAdded(movieDummy: Production) {
        var list = favoritesListUseCase.moviesFlow.first()

        // should be empty because the list is cleared everytime the test starts.
        assert(list.isEmpty())

        // adding it to the favorites list
        favoritesViewModel.toggleFavoriteStatus(movieDummy, false)

        // getting the list of favorites
        list = favoritesListUseCase.moviesFlow.first { it.isNotEmpty() }

        // making sure the list contains the item.
        assert(list.contains(movieDummy))

        // making sure the item is saved by checking through the id.
        assert(favoritesViewModel.isProductionAFavorite(movieDummy.id).first())
    }

    @Test
    fun ensureAFavoriteShowCanBeAdded(): Unit = runBlocking {
        addTheDummyShowFavoriteAndEnsureItIsAdded(Production.showDummy)
    }

    @Test
    fun ensureAFavoriteShowCanBeRemovedAfterBeingAdded(): Unit = runBlocking {
        val showDummy = Production.showDummy

        addTheDummyShowFavoriteAndEnsureItIsAdded(showDummy)

        favoritesViewModel.toggleFavoriteStatus(showDummy, true)

        // wait for the list to be emptied.
        val list = favoritesListUseCase.showsFlow.first { it.isEmpty() }

        // should be empty because only one item should have been there.
        assert(list.isEmpty())
    }

    /**
     * After this executes successfully, the show dummy object will be in the favorite list.
     * */
    private suspend fun addTheDummyShowFavoriteAndEnsureItIsAdded(showDummy: Production) {
        var list = favoritesListUseCase.showsFlow.first()

        // should be empty because the list is cleared everytime the test starts.
        assert(list.isEmpty())

        // adding it to the favorites list
        favoritesViewModel.toggleFavoriteStatus(showDummy, false)

        // getting the list of favorites
        list = favoritesListUseCase.showsFlow.first { it.isNotEmpty() }

        // making sure the list contains the item.
        assert(list.contains(showDummy))

        // making sure the item is saved by checking through the id.
        assert(favoritesViewModel.isProductionAFavorite(showDummy.id).first())
    }

    /**
     * Since I am using a composable function to get an instance of the viewModel, I must call it
     * from a composable function, there for, the instance is being obtained from the context of
     *  setContent method
     * */
    private fun instantiateViewModel() {
        composeTestRule.setContent { favoritesViewModel = viewModel() }
    }

    private fun deleteAllFavorites() = runBlocking { favoritesListUseCase.deleteAll() }
}
