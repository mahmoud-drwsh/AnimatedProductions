package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.Resource
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.navigateUp
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredLoadingMessageWithIndicator
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CenteredText
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.*


class ProductionDetailsActivity : ComponentActivity() {

    private val viewModel by viewModels<ProductionDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadProductionObject(getProductionObject())

        setContent {
            val state by viewModel.productionObject.collectAsState(initial = Resource.Loading)

            updateIdlingResourceStatus(state)

            AnimatedProductionsTheme {
                DetailsScreen(state) {
                    navigateUp()
                }
            }
        }
    }

    companion object {
        private const val PRODUCTION_INTENT_KEY = "production_key"

        private fun Context.newIntentWithExtra(production: Production): Intent =
            Intent(this, ProductionDetailsActivity::class.java)
                .apply { putExtra(PRODUCTION_INTENT_KEY, production) }

        fun Context.navigateToDetails(production: Production) =
            startActivity(newIntentWithExtra(production))

        internal fun ProductionDetailsActivity.getProductionObject(): Production? =
            intent.getParcelableExtra(PRODUCTION_INTENT_KEY)
    }

    private fun updateIdlingResourceStatus(productionResource: Resource<Production>) =
        DetailsActivityIdlingResource.setIdleState(productionResource is Resource.Success)
}

@Composable
fun DetailsScreen(detailsUIState: Resource<Production>, navigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.production_details)) },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back_button_desc)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            Modifier.padding(paddingValues)
        ) {
            when (detailsUIState) {
                is Resource.Error -> CenteredText(text = detailsUIState.message)
                is Resource.Loading -> CenteredLoadingMessageWithIndicator(
                    Modifier.testTag(DETAILS_LOADING_INDICATOR_TEST_TAG)
                )
                is Resource.Success -> ProductionDetailsContent(detailsUIState.data)
            }
        }
    }
}

@Composable
private fun ProductionDetailsContent(detailsUIState: Production) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = spacedBy(DETAILS_CARD_COLUMN_SPACED_BY),
    ) {
        CoilImage(url = detailsUIState.backdropPath,
            imageDescription = stringResource(R.string.backdrop_image_description),
            modifier = Modifier
                .fillMaxWidth()
                .height(BACKDROP_HEIGHT)
                .testTag(DETAILS_BACKDROP_IMAGE_TEST_TAG))

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
                    url = detailsUIState.posterPath,
                    imageDescription = stringResource(R.string.poster_image_description),
                    Modifier
                        .width(128.dp)
                        .testTag(DETAILS_POSTER_IMAGE_TEST_TAG)
                )

                Column(
                    verticalArrangement = spacedBy(DETAILS_CONTENT_LOWER_PART_PADDING),
                ) {
                    Text(text = detailsUIState.name, style = MaterialTheme.typography.h5)

                    Text(text = detailsUIState.firstAirDate,
                        style = MaterialTheme.typography.subtitle1)


                }
            }

            Text(text = detailsUIState.overview, style = MaterialTheme.typography.body1)

            Divider(Modifier.fillMaxWidth())

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = spacedBy(DETAILS_TEXT_AND_ICON_SPACE_BY),
                    ) {
                        Text(text = detailsUIState.voteAverage.toString())
                        Icon(Icons.Default.Star,
                            contentDescription = stringResource(R.string.vote_average_star_icon_desc))
                    }
                    Text(
                        text = stringResource(id = R.string.votes_with_number,
                            detailsUIState.voteCount)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = spacedBy(DETAILS_TEXT_AND_ICON_SPACE_BY),
                    ) {
                        Text(text = detailsUIState.originalLanguage.uppercase())
                        Icon(Icons.Default.Language,
                            contentDescription = stringResource(R.string.production_language_icon_desc))
                    }
                    Text(text = stringResource(R.string.original_language))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = detailsUIState.popularity.toString())
                    Text(text = stringResource(R.string.popularity))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetails() {
    DetailsScreen(detailsUIState = Resource.Success(Production.dummy)) {}
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    DetailsScreen(detailsUIState = Resource.Loading) {}
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsError() {
    DetailsScreen(detailsUIState = Resource.Error("TEST_ERROR_MESSAGE")) {}
}
