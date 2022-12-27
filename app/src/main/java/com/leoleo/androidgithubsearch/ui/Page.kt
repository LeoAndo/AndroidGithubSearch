package com.leoleo.androidgithubsearch.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Page(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Filled.Home),
    SEARCH("Search", Icons.Filled.Search),
    User("User", Icons.Filled.Face)
}