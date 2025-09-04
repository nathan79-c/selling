package com.example.selling.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selling.data.model.Product
import com.example.selling.data.repository.abstract_interface.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Empty)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun createProduct(product: Product) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.createProduct(product)
            _uiState.value = result.fold(
                onSuccess = { ProductUiState.Success() },
                onFailure = { ProductUiState.Error(it.message ?: "Erreur lors de la création") }
            )
        }
    }

    fun getAllProductsExceptMine() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.AllProductWithoutMy()
            _uiState.value = result.fold(
                onSuccess = { products ->
                    if (products.isEmpty()) ProductUiState.Empty
                    else ProductUiState.Success(products)
                },
                onFailure = { ProductUiState.Error(it.message ?: "Erreur de récupération") }
            )
        }
    }

    fun getMyProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.MyAllProduct()
            _uiState.value = result.fold(
                onSuccess = { products ->
                    if (products.isEmpty()) ProductUiState.Empty
                    else ProductUiState.Success(products)
                },
                onFailure = { ProductUiState.Error(it.message ?: "Erreur de récupération") }
            )
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.updateProduct(product)
            _uiState.value = result.fold(
                onSuccess = { ProductUiState.Success() },
                onFailure = { ProductUiState.Error(it.message ?: "Erreur de mise à jour") }
            )
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.deleteProduct(productId)
            _uiState.value = result.fold(
                onSuccess = { ProductUiState.Success() },
                onFailure = { ProductUiState.Error(it.message ?: "Erreur de suppression") }
            )
        }
    }

    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = productRepository.getProductById(productId)
            _uiState.value = result.fold(
                onSuccess = { product -> ProductUiState.SuccessProduct(product) },
                onFailure = { e -> ProductUiState.Error(e.message ?: "Unknown error") }
            )
        }
    }
}

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: List<Product> = emptyList()) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
    data class SuccessProduct(val product: Product?) : ProductUiState()
    object Empty : ProductUiState()
}
