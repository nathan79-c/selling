package com.example.selling.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.selling.data.model.Product
import com.example.selling.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()
}

sealed class ProductUiState{
    data object Loading: ProductUiState()
    data class Success(val movies: List<Product> = emptyList()): ProductUiState()
    data class Error(val message: String): ProductUiState()

}