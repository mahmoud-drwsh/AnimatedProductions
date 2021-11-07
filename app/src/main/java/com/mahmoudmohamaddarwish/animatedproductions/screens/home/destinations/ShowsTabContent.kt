package com.mahmoudmohamaddarwish.animatedproductions.screens.home.destinations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.ProductionsGridList
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MAIN_ACTIVITY_SHOWS_LOADING_INDICATOR_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowsTabContent(
    productionsViewModel: ProductionsViewModel = hiltViewModel(),
    orderedShowsFlow: Flow<Resource<List<Production>>> = productionsViewModel.orderedShowsFlow,
) {
    val resource by orderedShowsFlow.collectAsState(initial = Resource.Loading)

    when (resource) {
        is Resource.Error -> {
            val error = resource as Resource.Error
            CenteredText(text = error.message)
        }

        is Resource.Loading -> CenteredLoadingMessageWithIndicator(
            Modifier.testTag(MAIN_ACTIVITY_SHOWS_LOADING_INDICATOR_TEST_TAG)
        )

        is Resource.Success -> {
            val success = resource as Resource.Success<List<Production>>
            ProductionsGridList(success, MAIN_ACTIVITY_SHOWS_LIST_TEST_TAG)
        }
    }
}