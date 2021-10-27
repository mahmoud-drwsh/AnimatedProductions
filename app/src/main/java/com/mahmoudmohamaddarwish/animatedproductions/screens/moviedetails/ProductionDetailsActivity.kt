package com.mahmoudmohamaddarwish.animatedproductions.screens.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Production
import com.mahmoudmohamaddarwish.animatedproductions.ui.components.CoilImage
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.AnimatedProductionsTheme
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.BACKDROP_HEIGHT
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.DETAILS_CARD_COLUMN_SPACED_BY
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.DETAILS_CARD_HORIZONTAL_PADDING

private const val TAG = "ProductionDetailsActivi"

class ProductionDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productionObject = getProductionObject()

        Log.e(TAG, "onCreate: $productionObject")

        setContent {
            DetailsScreen(productionObject)
        }
    }

    companion object {
        private const val PRODUCTION_INTENT_KEY = "production_key"

        private fun Context.newIntentWithExtra(production: Production): Intent =
            Intent(this, ProductionDetailsActivity::class.java)
                .apply { putExtra(PRODUCTION_INTENT_KEY, production) }

        fun Context.navigateToDetails(production: Production) {
            val intent = newIntentWithExtra(production)
            startActivity(intent)
        }

        internal fun ProductionDetailsActivity.getProductionObject(): Production =
            intent.getParcelableExtra<Production>(PRODUCTION_INTENT_KEY) as Production
    }

}

@Composable
fun DetailsScreen(productionObject: Production) {
    AnimatedProductionsTheme {
        val state = rememberScrollState()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state),
            verticalArrangement = spacedBy(DETAILS_CARD_COLUMN_SPACED_BY),
        ) {
            CoilImage(url = productionObject.backdropPath,
                imageDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BACKDROP_HEIGHT))

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = spacedBy(8.dp),
                ) {
                    CoilImage(
                        url = productionObject.posterPath, imageDescription = "",
                        Modifier.width(128.dp)
                    )

                    Column(
                        verticalArrangement = spacedBy(8.dp),
                    ) {
                        Text(text = productionObject.name, style = MaterialTheme.typography.h5)

                        Text(text = productionObject.firstAirDate,
                            style = MaterialTheme.typography.subtitle1)


                    }
                }

                Text(text = productionObject.overview, style= MaterialTheme.typography.body1)

                Divider(Modifier.fillMaxWidth())

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = productionObject.voteAverage.toString())
                        Icon(Icons.Default.Star, contentDescription = "")
                    }
                    Text(text = "${productionObject.voteCount} votes")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetails() {
    val productionObject =
        Production(backdropPath = "https://image.tmdb.org/t/p/w780//hwwFyowfcbLRVmRBOkvnABBNIOs.jpg",
            firstAirDate = "1998-11-25",
            id = 9487,
            name = """A Bug's Life""",
            originalLanguage = "en",
            overview = """On behalf of "oppressed bugs everywhere," an inventive ant named Flik hires a troupe of warrior bugs to defend his bustling colony from a horde of freeloading grasshoppers led by the evil-minded Hopper.""",
            popularity = 67.211,
            posterPath = "https://image.tmdb.org/t/p/w500//hFamOus53922agTlKxhcL7ngJ9h.jpg",
            voteAverage = 7.0,
            voteCount = 7252)

    DetailsScreen(productionObject = productionObject)
}