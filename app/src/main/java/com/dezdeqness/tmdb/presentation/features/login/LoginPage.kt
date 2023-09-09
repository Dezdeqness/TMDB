package com.dezdeqness.tmdb.presentation.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dezdeqness.tmdb.navigation.HOME_ROUTE
import com.dezdeqness.tmdb.navigation.LOGIN_ROUTE

@Composable
fun LoginPage(
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                navController.navigate(HOME_ROUTE) {
                    popUpTo(LOGIN_ROUTE) {
                        inclusive = true
                    }
                }

            },
    ) {
        Text("LoginPage")
    }
}
