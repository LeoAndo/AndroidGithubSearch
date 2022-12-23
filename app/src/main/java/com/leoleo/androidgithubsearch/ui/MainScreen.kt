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
import com.leoleo.androidgithubsearch.ui.search.SearchScreen
import com.leoleo.androidgithubsearch.ui.theme.AndroidGithubSearchTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    AndroidGithubSearchTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                MyNavigationGraph(startDestination = TopDestinations.SearchRoute.routeName)
            }
        }
    }
}

@Composable
private fun MyNavigationGraph(
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
            startDestination = SearchDestinations.TopRoute.routeName,
            route = TopDestinations.SearchRoute.routeName,
        ) {
            composable(
                route = SearchDestinations.TopRoute.routeName,
                content = {
                    SearchScreen(
                        modifier = modifier,
                        navigateToDetailScreen = { ownerName, name ->
                            navController.navigate(
                                SearchDestinations.DetailRoute.withArgs(
                                    ownerName,
                                    name,
                                )
                            )
                        },
                    )
                }
            )
            composable(
                route = SearchDestinations.DetailRoute.routeName + "/{ownerName}/{name}",
                arguments = listOf(
                    navArgument("ownerName") {
                        type = NavType.StringType
                        nullable = false
                    },
                    navArgument("name") {
                        type = NavType.StringType
                        nullable = false
                    }
                ),
                content = {
                    val ownerName = it.arguments?.getString("ownerName") ?: return@composable
                    val name = it.arguments?.getString("name") ?: return@composable
                    // TODO: 別イシューにてリポジトリ詳細画面の処理を書く.
                }
            )
        }
    }
}