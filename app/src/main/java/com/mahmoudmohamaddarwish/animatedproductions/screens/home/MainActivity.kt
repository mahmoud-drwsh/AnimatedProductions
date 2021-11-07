package com.mahmoudmohamaddarwish.animatedproductions.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.component.MainAppBar
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.destinations.BottomNavigationDestination
import com.mahmoudmohamaddarwish.animatedproductions.screens.home.destinations.BottomNavigationDestination.Companion.destinations
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { MainNav() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav(navController: NavHostController = rememberNavController()) {

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBackStackEntry?.destination?.route == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = null) },
                        label = destination.title
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController,
            BottomNavigationDestination.Movies.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            destinations.forEach { destination ->
                composable(route = destination.route) { stackEntry: NavBackStackEntry ->
                    Scaffold(
                        topBar = {
                            MainAppBar(destination.title)
                        }
                    ) { paddingValues ->
                        Box(Modifier.padding(paddingValues)) {
                            destination.content(stackEntry)
                        }
                    }
                }
            }
        }
    }
}

