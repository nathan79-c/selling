package com.example.selling.data.repository

import com.example.selling.data.model.Product
import com.example.selling.data.repository.abstract_interface.ProductRepository
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class ProductRepositoryImpl(private val auth: FirebaseAuth,private val db: FirebaseFirestore): ProductRepository{

    private  val productCollection = db.collection("products")

    override suspend fun createProduct(product: Product): Result<Unit> {
        return try {
            productCollection.add(product).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun AllProductWithoutMy(): Result<List<Product>> {
        return try {
            val currentUserId = auth.currentUser?.uid ?: return Result.failure(Exception("User not authenticated"))
            val snapshot = productCollection.whereNotEqualTo("sellerId", currentUserId).get().await()
            val products = snapshot.toObjects(Product::class.java)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun MyAllProduct(): Result<List<Product>> {
        return try {
            val currentUserId = auth.currentUser?.uid ?: return Result.failure(Exception("User not authenticated"))
            val snapshot = productCollection.whereEqualTo("sellerId", currentUserId).get().await()
            val products = snapshot.toObjects(Product::class.java)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            if (product.id == null) return Result.failure(Exception("Product ID is required for update"))
            productCollection.document(product.id).set(product).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            productCollection.document(productId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}