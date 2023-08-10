package com.bladoae.imdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bladoae.imdb.base.common.NavigationOptions
import com.bladoae.imdb.base.common.Resource
import com.bladoae.imdb.presentation.createaccount.CreateAccountScreen
import com.bladoae.imdb.presentation.home.HomeScreen
import com.bladoae.imdb.presentation.login.LoginScreen
import com.bladoae.imdb.presentation.theme.IMDbTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var keepSplash = true
    private var startDestination = NavigationOptions.LOGIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateUser()

        installSplashScreen().setKeepOnScreenCondition { keepSplash }

        setContent {
            val navController = rememberNavController()

            IMDbTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController, startDestination = startDestination.path) {
                    composable(route = NavigationOptions.LOGIN.path) {
                        LoginScreen(navController)
                    }
                    composable(route = NavigationOptions.CREATE_ACCOUNT.path) {
                        CreateAccountScreen(navController)
                    }
                    composable(route = NavigationOptions.HOME.path) {
                        HomeScreen()
                    }
                }
            }
        }

    }

    private fun validateUser() {
        viewModel.login.observe(this) { response ->
            if(response is Resource.Success) {
                startDestination = NavigationOptions.HOME
            }
            keepSplash = false
        }
        viewModel.isUserLoggedIn()
    }
}