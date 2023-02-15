package com.leoleo.androidgithubsearch.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
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
                        ownerName = ownerName,
                        name = name,
                    )
                }
            )
        }
    }
}