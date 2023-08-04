package com.bladoae.imdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bladoae.imdb.presentation.home.HomeScreen
import com.bladoae.imdb.presentation.theme.IMDbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            IMDbTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}