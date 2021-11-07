package com.mahmoudmohamaddarwish.animatedproductions.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.PRODUCTIONS_GRID_CELLS_NUMBER
import com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails.ProductionDetailsActivity.Companion.navigateToDetails
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.POSTER_IMAGE_HEIGHT
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.PRODUCTION_POSTER_IMAGE_PADDING

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductionsGridList(resource: Resource.Success<List<Production>>, testTag: String) {
    val context = LocalContext.current

    LazyVerticalGrid(
        cells = GridCells.Fixed(PRODUCTIONS_GRID_CELLS_NUMBER),
        contentPadding = PaddingValues(top = 0.dp, start = 4.dp, end = 4.dp, bottom = 4.dp),
        modifier = Modifier.testTag(testTag)
    ) {
        items(resource.data) { production ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                CoilImage(
                    url = production.posterPath,
                    imageDescription = stringResource(R.string.poster_image_description),
                    modifier = Modifier
                        .padding(PRODUCTION_POSTER_IMAGE_PADDING)
                        .height(POSTER_IMAGE_HEIGHT)
                        .fillMaxWidth()
                        .clickable { context.navigateToDetails(production) }
                        .testTag(MAIN_ACTIVITY_POSTER_IMAGE_TEST_TAG))
            }
        }
    }
}