package com.example.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweatherforecast.screens.search.SearchScreen
import com.example.jetweatherforecast.screens.WeatherFavoriteScreen
import com.example.jetweatherforecast.screens.main.WeatherMainScreen
import com.example.jetweatherforecast.screens.WeatherSplashScreen
import com.example.jetweatherforecast.screens.about.AboutScreen
import com.example.jetweatherforecast.screens.settings.SettingsScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            WeatherFavoriteScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name

        composable("$route/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) {navBack ->
            navBack.arguments?.getString("city").let {
                city->
                //            val viewModel = hiltViewModel<MainViewModel>()
                WeatherMainScreen(navController = navController, city = city)
            }

        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}






