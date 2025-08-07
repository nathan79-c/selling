package com.example.selling.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.selling.ui.navigation.GraphRoute
import com.example.selling.ui.navigation.Screen
import com.example.selling.ui.screens.Authenification.LoginScreen
import com.example.selling.ui.screens.Authenification.SignUpScreen

@Composable
fun BoutiqueApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "",
    ) {
        composable<Screen.Home> {
        }
    }
}

fun NavGraphBuilder.authGraph(navController: NavController){
    navigation<GraphRoute.AuthGraph>(startDestination = Screen.Login){
        composable<Screen.Login>{
            LoginScreen {  }
        }
        composable<Screen.SignUp> {
            SignUpScreen {  }
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController){
    navigation<GraphRoute.MainGraph>(startDestination = Screen.Home){
        composable<Screen.Home>{

        }

    }
}