package com.example.selling.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.selling.R
import com.example.selling.ui.navigation.GraphRoute
import com.example.selling.ui.navigation.Screen
import com.example.selling.ui.screens.Authenification.LoginScreen
import com.example.selling.ui.screens.Authenification.SignUpScreen
import com.example.selling.ui.screens.product.CreateEditProductScreen
import com.example.selling.ui.screens.product.HomeScreen
import com.example.selling.ui.screens.product.ProductDetailScreen
import com.example.selling.ui.screens.profil.SellerDashboardScreen
import com.example.selling.ui.screens.profil.SellerProfileScreen
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

    // observe l'état d'auth
    val authState by authViewModel.state.collectAsState()

    val startDestination = if (authState.isAuthenticated) {
        GraphRoute.MainGraph
    } else {
        GraphRoute.AuthGraph
    }

    Scaffold(
        topBar = {
            // Affiche la TopBar uniquement si l'utilisateur est connecté
            if (authState.isAuthenticated) {
                BoutiqueTopBar(
                    onProfileClick = {
                        navController.navigate("profile") // ou ton screen profil
                    },
                    onLogoutClick = {
                        authViewModel.signOut() // ta logique de logout
                        navController.navigate(GraphRoute.AuthGraph) {
                            popUpTo(0) // nettoie la backstack
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            authGraph(navController, authViewModel)
            mainGraph(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoutiqueTopBar(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text("MaBoutique", fontWeight = FontWeight.Bold)
        },
        actions = {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.me),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable { showMenu = !showMenu }
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Voir profil") },
                        onClick = {
                            showMenu = false
                            onProfileClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Se déconnecter") },
                        onClick = {
                            showMenu = false
                            onLogoutClick()
                        }
                    )
                }
            }
        }
    )
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

        }/*
        composable<Screen.DetailsProduct>{
            ProductDetailScreen()
        }
       composable<Screen.Seller>{
           SellerProfileScreen()
       }*/

    }
}

/*fun NavGraphBuilder.sellerGraph(navController: NavController){
    navigation<GraphRoute.SellerGraph>(startDestination = Screen.Dashboard){
        composable<Screen.Dashboard> {
            SellerDashboardScreen() { }
        }
        composable<Screen.CreateEditProduct> {
            CreateEditProductScreen()
        }

        composable < Screen.EditProfil>{
            SellerProfileScreen()
        }
    }
} */