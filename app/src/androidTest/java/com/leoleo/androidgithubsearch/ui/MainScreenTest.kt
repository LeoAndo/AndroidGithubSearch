package com.leoleo.androidgithubsearch.ui

import android.content.Context
import android.content.res.Resources
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
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

    private lateinit var resources: Resources

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        resources = ApplicationProvider.getApplicationContext<Context>().resources
    }

    @Test
    fun show_compact_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Compact) }
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.test_tag_compact_main_screen)).assertIsDisplayed()
        }
    }

    @Test
    fun show_medium_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Medium) }
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.test_tag_medium_main_screen)).assertIsDisplayed()
        }
    }

    @Test
    fun show_expanded_main_screen() {
        composeTestRule.activity.setContent { MainScreen(WindowWidthSizeClass.Expanded) }
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.test_tag_expanded_main_screen)).assertIsDisplayed()
        }
    }
}