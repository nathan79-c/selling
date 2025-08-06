package com.example.selling.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selling.ui.navigation.Screen

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