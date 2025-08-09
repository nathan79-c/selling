package com.example.selling.ui

import androidx.lifecycle.ViewModel
import com.example.selling.data.model.Product
import com.example.selling.data.repository.ProductRepository


class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {
}

sealed class ProductUiState{
    data object Loading: ProductUiState()
    data class Success(val movies: List<Product>): ProductUiState()
    data class Error(val message: String): ProductUiState()

}