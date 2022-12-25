package com.leoleo.androidgithubsearch.ui

interface Destinations {
    val routeName: String
}

sealed interface TopDestinations : Destinations {
    object SearchRoute : TopDestinations {
        override val routeName: String = "search"
    }
}

// nest Navigation
sealed interface GithubRepoSearchDestinations : Destinations {
    object TopRoute : GithubRepoSearchDestinations {
        override val routeName: String = "top"
    }

    object DetailRoute : GithubRepoSearchDestinations {
        const val ARG_KEY_OWNER_NAME = "ownerName"
        const val ARG_KEY_NAME = "name"
        const val routeNameWithArgs: String = "detail/{$ARG_KEY_OWNER_NAME}/{$ARG_KEY_NAME}"
        override val routeName: String = "detail"
        fun withArgs(vararg args: String): String {
            return buildString {
                append(routeName)
                args.forEach { arg ->
                    append("/$arg")
                }
            }
        }
    }
}