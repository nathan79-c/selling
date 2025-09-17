package com.example.selling.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    //Client Screen

    @Serializable
    data object Home: Screen
    @Serializable
    data class DetailsProduct(val productId: Int): Screen
    @Serializable
    data class  Seller(val sellerId:Int): Screen

    //  Seller Screen

    @Serializable
    data class CreateEditProduct(val product:Int?): Screen
    @Serializable
    data object EditProfil: Screen
    @Serializable
    data object Dashboard:Screen

    // Authentification

    @Serializable
    data object Login: Screen
    @Serializable
    data object SignUp:Screen

}

sealed interface GraphRoute{
    @Serializable data object AuthGraph: GraphRoute
    @Serializable data object MainGraph: GraphRoute

    @Serializable data object SellerGraph: GraphRoute
}
