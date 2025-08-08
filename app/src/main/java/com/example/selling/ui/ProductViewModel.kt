package com.example.selling.ui

import androidx.lifecycle.ViewModel
import com.example.selling.data.model.Product


class ProductViewModel: ViewModel() {
}

sealed class ProductUiState{
    data object Loading: ProductUiState()
    data class Success(val movies: List<Product>): ProductUiState()
    data class Error(val message: String): ProductUiState()

}