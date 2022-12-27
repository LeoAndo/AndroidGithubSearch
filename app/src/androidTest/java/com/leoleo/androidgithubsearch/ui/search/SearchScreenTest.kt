package com.leoleo.androidgithubsearch.ui.search

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leoleo.androidgithubsearch.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import com.leoleo.androidgithubsearch.R
import com.leoleo.androidgithubsearch.MainActivity
import com.leoleo.androidgithubsearch.ui.MainScreen

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // The strings used for matching in these tests
    private lateinit var tagSearchScreen: String

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.apply {
            tagSearchScreen = getString(R.string.test_tag_search_screen)
        }
    }

    @Test
    fun show_compact_search_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Compact) }
        with(composeTestRule) {
            onNodeWithTag(tagSearchScreen).assertIsDisplayed()
        }
    }
}