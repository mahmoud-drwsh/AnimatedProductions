package com.mahmoudmohamaddarwish.animatedproductions.screens.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.data.model.domain.Production
import com.mahmoudmohamaddarwish.animatedproductions.navigateUp
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.screens.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.FavoritesViewModel
import com.mahmoudmohamaddarwish.animatedproductions.screens.shared_viewmodels.ProductionDetailsViewModel
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme3.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow


@AndroidEntryPoint
class ProductionDetailsActivity : ComponentActivity() {

    private val productionDetailsViewModel by viewModels<ProductionDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productionDetailsViewModel.loadProductionObject(getProductionObject())

        setContent {
            val detailsUIState by productionDetailsViewModel.productionObjectFlow
                .collectAsState(initial = Resource.Loading)

            AppTheme {
                DetailsScreen(detailsUIState = detailsUIState, navigateBack = { navigateUp() })
            }
        }
    }

    companion object {
        private const val PRODUCTION_OBJECT_INTENT_KEY = "production_key"

        private fun Context.newIntentWithExtra(production: Production): Intent =
            Intent(this, ProductionDetailsActivity::class.java)
                .apply { putExtra(PRODUCTION_OBJECT_INTENT_KEY, production) }

        fun Context.navigateToDetails(production: Production) =
            startActivity(newIntentWithExtra(production))

        internal fun ProductionDetailsActivity.getProductionObject(): Production? =
            intent.getParcelableExtra(PRODUCTION_OBJECT_INTENT_KEY)
    }
}

@Composable
fun DetailsScreen(
    detailsUIState: Resource<Production>,
    navigateBack: () -> Unit,
) = when (detailsUIState) {
    is Resource.Error -> CenteredText(
        text = detailsUIState.message,
        Modifier.testTag(DetailsActivityTestTags.ERROR_MESSAGE.name)
    )
    is Resource.Loading -> CenteredLoadingMessageWithIndicator(
        Modifier.testTag(DetailsActivityTestTags.LOADING_INDICATOR.name)
    )
    is Resource.Success -> DetailsScreenSuccessContent(
        detailsUIState = detailsUIState,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenSuccessContent(
    detailsUIState: Resource.Success<Production>,
    navigateBack: () -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    val isProductionAFavoriteFlow: Flow<Boolean> = remember {
        favoritesViewModel.isProductionAFavorite(detailsUIState.data.id)
    }

    val isProductionAFavorite by isProductionAFavoriteFlow.collectAsState(initial = false)

    Scaffold(
        topBar = {
            ProductionDetailsAppBar(
                navigateBack = navigateBack,
                favoritesViewModel = favoritesViewModel,
                detailsUIState = detailsUIState,
                isProductionAFavorite = isProductionAFavorite
            )
        },
        modifier = Modifier.testTag(DetailsActivityTestTags.ROOT_COMPOSABLE.name)
    ) { paddingValues ->
        ProductionDetailsSuccessScaffoldContent(
            Modifier.padding(paddingValues),
            detailsUIState
        )
    }
}

@Composable
private fun ProductionDetailsAppBar(
    navigateBack: () -> Unit,
    favoritesViewModel: FavoritesViewModel,
    detailsUIState: Resource.Success<Production>,
    isProductionAFavorite: Boolean,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.production_details)) },
        navigationIcon = {
            IconButton(
                onClick = { navigateBack() },
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back_button_desc)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    favoritesViewModel.toggleFavoriteStatus(detailsUIState.data,
                        isProductionAFavorite)
                },
                modifier = Modifier.testTag(DetailsActivityTestTags.ROOT_FAVORITE_ICON.name)
            ) {
                val icon =
                    if (isProductionAFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder

                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.toggle_favorite_state_desc)
                )
            }
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
    )
}

@Composable
private fun ProductionDetailsSuccessScaffoldContent(
    modifier: Modifier,
    detailsUIState: Resource.Success<Production>,
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = spacedBy(DETAILS_CARD_COLUMN_SPACED_BY),
    ) {
        CoilImage(url = detailsUIState.data.backdropPath,
            imageDescription = stringResource(R.string.backdrop_image_description),
            modifier = Modifier
                .fillMaxWidth()
                .height(BACKDROP_HEIGHT)
                .padding(horizontal = 8.dp)
                .padding(top = 4.dp)
                .testTag(DetailsActivityTestTags.BACKDROP_IMAGE.name))

        Column(
            Modifier
                .fillMaxSize()
                .padding(DETAILS_CONTENT_LOWER_PART_PADDING),
            verticalArrangement = spacedBy(DETAILS_CONTENT_LOWER_PART_COLUMN_SPACE_BY),
        ) {
            Row(
                horizontalArrangement = spacedBy(DETAILS_CONTENT_LOWER_PART_PADDING),
            ) {
                CoilImage(
                    url = detailsUIState.data.posterPath,
                    imageDescription = stringResource(R.string.poster_image_description),
                    Modifier
                        .width(DETAILS_POSTER_IMAGE_WIDTH)
                        .testTag(DetailsActivityTestTags.POSTER_IMAGE.name)
                )

                Column(
                    verticalArrangement = spacedBy(DETAILS_CONTENT_LOWER_PART_PADDING),
                ) {
                    Text(
                        text = detailsUIState.data.name,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.testTag(DetailsActivityTestTags.TITLE_TEXT.name),
                    )

                    Text(
                        text = detailsUIState.data.firstAirDate,
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .testTag(DetailsActivityTestTags.OVERVIEW_TEXT.name)
            ) {
                Column(
                    verticalArrangement = spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )

                    Text(
                        text = detailsUIState.data.overview,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }

            Divider(Modifier.fillMaxWidth())

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(DetailsActivityTestTags.RATING_AND_LANGUAGE_ROW.name)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = spacedBy(DETAILS_TEXT_AND_ICON_SPACE_BY),
                    ) {
                        Text(text = detailsUIState.data.voteAverage.toString())
                        Icon(Icons.Default.Star,
                            contentDescription = stringResource(R.string.vote_average_star_icon_desc))
                    }
                    Text(
                        text = stringResource(id = R.string.votes_with_number,
                            detailsUIState.data.voteCount)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = spacedBy(DETAILS_TEXT_AND_ICON_SPACE_BY),
                    ) {
                        Text(text = detailsUIState.data.originalLanguage.uppercase())
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = stringResource(R.string.production_language_icon_desc)
                        )
                    }
                    Text(text = stringResource(R.string.original_language))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = detailsUIState.data.popularity.toString())
                    Text(text = stringResource(R.string.popularity))
                }
            }
        }
    }
}