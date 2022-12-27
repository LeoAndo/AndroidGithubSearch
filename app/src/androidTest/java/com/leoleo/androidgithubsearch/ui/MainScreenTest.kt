package com.leoleo.androidgithubsearch.ui

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

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // The strings used for matching in these tests
    private lateinit var tagCompactMainScreen: String
    private lateinit var tagMediumMainScreen: String
    private lateinit var tagExpandedMainScreen: String

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.apply {
            tagCompactMainScreen = getString(R.string.test_tag_compact_main_screen)
            tagMediumMainScreen = getString(R.string.test_tag_medium_main_screen)
            tagExpandedMainScreen = getString(R.string.test_tag_expanded_main_screen)
        }
    }

    @Test
    fun show_compact_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Compact) }
        with(composeTestRule) {
            onNodeWithTag(tagCompactMainScreen).assertIsDisplayed()
        }
    }

    @Test
    fun show_medium_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Medium) }
        with(composeTestRule) {
            onNodeWithTag(tagMediumMainScreen).assertIsDisplayed()
        }
    }

    @Test
    fun show_expanded_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Expanded) }
        with(composeTestRule) {
            onNodeWithTag(tagExpandedMainScreen).assertIsDisplayed()
        }
    }
}