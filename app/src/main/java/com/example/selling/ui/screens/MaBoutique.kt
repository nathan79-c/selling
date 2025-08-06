package com.example.selling.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BoutiqueApp(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "",
    ){
        composable<>{
    }
}