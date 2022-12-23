package com.leoleo.androidgithubsearch.ui

sealed class TopDestinations(val routeName: String) {
    object SearchRoute : TopDestinations(routeName = "search")
}

// nest Navigation
sealed class SearchDestinations(val routeName: String) {
    object TopRoute : SearchDestinations(routeName = "top")
    object DetailRoute : SearchDestinations(routeName = "detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(routeName)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}