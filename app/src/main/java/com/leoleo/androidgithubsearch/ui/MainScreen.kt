package com.leoleo.androidgithubsearch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.leoleo.androidgithubsearch.ui.GithubRepoSearchDestinations.DetailRoute.ARG_KEY_NAME
import com.leoleo.androidgithubsearch.ui.GithubRepoSearchDestinations.DetailRoute.ARG_KEY_OWNER_NAME
import com.leoleo.androidgithubsearch.ui.detail.DetailScreen
import com.leoleo.androidgithubsearch.ui.search.SearchScreen
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@Composable
fun MainScreen() {
    AndroidGithubSearchTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                MyNavHost(startDestination = TopDestinations.SearchRoute.routeName)
            }
        }
    }
}

@Composable
private fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        // nest navigation
        navigation(
            startDestination = GithubRepoSearchDestinations.TopRoute.routeName,
            route = TopDestinations.SearchRoute.routeName,
        ) {
            composable(
                route = GithubRepoSearchDestinations.TopRoute.routeName,
                content = {
                    SearchScreen(
                        modifier = modifier,
                        navigateToDetailScreen = { ownerName, name ->
                            navController.navigate(
                                GithubRepoSearchDestinations.DetailRoute.withArgs(
                                    ownerName,
                                    name,
                                )
                            )
                        },
                    )
                }
            )
            composable(
                route = GithubRepoSearchDestinations.DetailRoute.routeNameWithArgs,
                arguments = listOf(
                    navArgument(ARG_KEY_OWNER_NAME) {
                        type = NavType.StringType
                        nullable = false
                    },
                    navArgument(ARG_KEY_NAME) {
                        type = NavType.StringType
                        nullable = false
                    },
                ),
                content = {
                    val ownerName = it.arguments?.getString(ARG_KEY_OWNER_NAME) ?: return@composable
                    val name = it.arguments?.getString(ARG_KEY_NAME) ?: return@composable
                    DetailScreen(
                        modifier = modifier,
                        ownerName = ownerName,
                        name = name,
                    )
                }
            )
        }
    }
}