package com.bladoae.imdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bladoae.imdb.presentation.home.HomeScreen
import com.bladoae.imdb.presentation.login.LoginScreen
import com.bladoae.imdb.presentation.theme.IMDbTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            IMDbTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController, startDestination = "login") {
                    composable(route = "login") {
                        LoginScreen(navController)
                    }
                    composable(route = "home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}