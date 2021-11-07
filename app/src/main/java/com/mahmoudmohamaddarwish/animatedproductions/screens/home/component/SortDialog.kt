package com.mahmoudmohamaddarwish.animatedproductions.screens.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudmohamaddarwish.animatedproductions.R
import com.mahmoudmohamaddarwish.animatedproductions.domain.model.Order
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.MAIN_ACTIVITY_SORTING_ICON_BUTTON_TEST_TAG
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.viewmodels.ProductionsOrderViewModel
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_HEIGHT
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_HORIZONTAL_PADDING
import com.mahmoudmohamaddarwish.animatedproductions.ui.theme.SORT_MENU_OPTION_TEXT_START_PADDING

@Composable
fun SortDialog(viewModel: ProductionsOrderViewModel = hiltViewModel()) {
    var shown by remember { mutableStateOf(false) }

    val order by viewModel.order.collectAsState(initial = Order.default)

    IconButton(
        onClick = { shown = true },
        modifier = Modifier.testTag(MAIN_ACTIVITY_SORTING_ICON_BUTTON_TEST_TAG),
    ) {
        Icon(Icons.Default.Sort,
            contentDescription = stringResource(R.string.sort_productions_icon_description))
    }

    if (shown)
        Dialog(onDismissRequest = { shown = false }) {
            Card {
                val propertyRadioOptions: List<Order.Property> = listOf(Order.Property.Name,
                    Order.Property.RELEASE_DATE)

                val typeRadioOptions: List<Order.Type> = listOf(Order.Type.ASCENDING,
                    Order.Type.DESCENDING)

                Column(Modifier.selectableGroup()) {
                    propertyRadioOptions.forEach { orderProperty ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(SORT_MENU_OPTION_HEIGHT)
                                .selectable(
                                    selected = (orderProperty == order.property),
                                    onClick = {
                                        viewModel.setSortProperty(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = SORT_MENU_OPTION_HORIZONTAL_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.property),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = SORT_MENU_OPTION_HORIZONTAL_PADDING)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = when (orderProperty) {
                                    Order.Property.Name -> Icons.Default.SortByAlpha
                                    Order.Property.RELEASE_DATE -> Icons.Default.Schedule
                                },
                                contentDescription = null,
                            )
                        }
                    }

                    Divider()

                    typeRadioOptions.forEach { orderProperty ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(SORT_MENU_OPTION_HEIGHT)
                                .selectable(
                                    selected = (orderProperty == order.type),
                                    onClick = {
                                        viewModel.setSortType(orderProperty)
                                        shown = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = SORT_MENU_OPTION_HORIZONTAL_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (orderProperty == order.type),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = orderProperty.label,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = SORT_MENU_OPTION_TEXT_START_PADDING)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = when (orderProperty) {
                                    Order.Type.ASCENDING -> Icons.Default.ArrowDownward
                                    Order.Type.DESCENDING -> Icons.Default.ArrowUpward
                                },
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }

}