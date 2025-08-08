package com.example.selling.ui



class ProductViewModel {
}

sealed class ProductUiState{
    data object Loading: ProductUiState()
    data object Success: ProductUiState()
    data class Error(val message: String): ProductUiState()

}