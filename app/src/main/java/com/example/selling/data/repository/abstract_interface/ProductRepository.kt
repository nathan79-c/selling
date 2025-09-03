package com.example.selling.data.repository.abstract_interface

import com.example.selling.data.model.Product

interface ProductRepository {

    suspend fun createProduct(product: Product): Result<Unit>
    suspend fun AllProductWithoutMy(): Result<List<Product>>
    suspend fun MyAllProduct(): Result<List<Product>>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(productId: String): Result<Unit>


}