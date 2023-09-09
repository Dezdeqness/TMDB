package com.dezdeqness.tmdb.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dezdeqness.tmdb.navigation.HOME_ROUTE
import com.dezdeqness.tmdb.navigation.LOGIN_ROUTE
import com.dezdeqness.tmdb.presentation.features.home.HomePage
import com.dezdeqness.tmdb.presentation.features.login.LoginPage

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LOGIN_ROUTE,
        modifier = modifier.fillMaxSize(),
    ) {
        composable(LOGIN_ROUTE) {
            LoginPage(navController = navController)
        }
        composable(HOME_ROUTE) {
            HomePage()
        }
    }

}
