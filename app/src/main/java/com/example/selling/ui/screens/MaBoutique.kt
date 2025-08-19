package com.example.selling.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.selling.ui.screens.product.HomeScreen
import com.example.selling.ui.viewModel.AuthUiState
import com.example.selling.ui.viewModel.AuthViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.viewmodel.factory.KoinViewModelFactory
import org.koin.viewmodel.getViewModelKey

@Composable
fun BoutiqueApp() {
    val authViewModel: AuthViewModel = koinViewModel()
    val navController = rememberNavController()
    val authState: StateFlow<AuthUiState> = authViewModel.state

    // Observe l'état d'authentification
    val startDestination = if (authState.collectAsState().value.isAuthenticated) {
        GraphRoute.MainGraph
    } else {
        GraphRoute.AuthGraph
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(navController, authViewModel)
        mainGraph(navController)
    }
}


fun NavGraphBuilder.authGraph(navController: NavController,authViewModel: AuthViewModel){


    val authState: StateFlow<AuthUiState> = authViewModel.state
    navigation<GraphRoute.AuthGraph>(startDestination = Screen.SignUp){
        composable<Screen.Login>{
            LoginScreen (
                uiState = authState,
                conectMail = authViewModel::onEmailChange,
                connectPassword= authViewModel::onPasswordChange,
                onSignUpClick = { navController.navigate(Screen.SignUp) },
                onSignInClick = { authViewModel.signIn() },
                navController = navController
            )
        }
        composable<Screen.SignUp> {
            SignUpScreen(
                uiState = authState,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onConfirmPasswordChange = authViewModel::onConfirmPasswordChange,
                onSignUpClick = { authViewModel.signUp() },
                onSignInClick = { navController.navigate(Screen.Login) },
                navController = navController
            )

        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController){
    navigation<GraphRoute.MainGraph>(startDestination = Screen.Home){
        composable<Screen.Home>{
            HomeScreen()


        }

    }
}