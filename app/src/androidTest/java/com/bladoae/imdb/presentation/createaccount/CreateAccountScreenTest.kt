package com.bladoae.imdb.presentation.createaccount

import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bladoae.imdb.CountingIdlingResourceSingleton
import com.bladoae.imdb.presentation.login.LoginScreen
import com.bladoae.imdb.ui.MainActivity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@RunWith(AndroidJUnit4::class)
class CreateAccountScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
    }

    @Test
    fun whenAllElementsAreDisplayed() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()
    }

    @Test
    fun whenPerformEmailTextInput() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()

        // I change the input
        val input = "bladi"
        composeTestRule.onNodeWithText("Email").performTextInput(input)
        composeTestRule.onNodeWithText(input).assertIsDisplayed()
    }

    @Test
    fun whenPerformPasswordTextInput() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()

        // I change the input
        val input = "1234567"
        val realInput = "•••••••"
        composeTestRule.onNodeWithText("Password", substring = true).performTextInput(input)
        composeTestRule.onNodeWithText(realInput, substring = true).assertIsDisplayed()
    }

    @Test
    fun whenPerformWrongEmailTextInput() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()

        // I change the input
        val emailInput = "blad"
        composeTestRule.onNodeWithText("Email", substring = true).performTextInput(emailInput)

        val passwordInput = "A123%4567"
        composeTestRule.onNodeWithText("Password", substring = true).performTextInput(passwordInput)

        composeTestRule.onNodeWithText("Create account", substring = true).assertIsNotEnabled()
    }

    @Test
    fun whenPerformWrongPasswordTextInput() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()

        // I change the input
        val emailInput = "blad"
        composeTestRule.onNodeWithText("Email", substring = true).performTextInput(emailInput)

        val passwordInput = "1234567"
        composeTestRule.onNodeWithText("Password", substring = true).performTextInput(passwordInput)

        composeTestRule.onNodeWithText("Create account", substring = true).assertIsNotEnabled()
    }

    @Test
    fun whenPerformCorrectEmailAndPasswordTextInput() {
        // When
        composeTestRule.activity.setContent {
            val navController = rememberNavController()
            CreateAccountScreen(navController)
        }

        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithContentDescription("logo").assertIsDisplayed()

        composeTestRule.onNodeWithText("Email", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Password", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Create account", substring = true).assertIsDisplayed()

        // I change the input
        val emailInput = "blad@gmail.com"
        composeTestRule.onNodeWithText("Email", substring = true).performTextInput(emailInput)

        val passwordInput = "A1-23/45b67"
        composeTestRule.onNodeWithText("Password", substring = true).performTextInput(passwordInput)

        composeTestRule.onNodeWithText("Create account", substring = true).assertIsEnabled()
    }

}