package com.example.selling.data.model

data class Product(
    val id: String? = null,               // généré par Firestore
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val sellerId: String = "",            // UID du user connecté
    val images: List<String>? = null,     // tableau d’URL, peut être null
    val status: String = "active"         // valeur par défaut
)

object ProductMapper {

    // Convert Firestore entity (Map ou DocumentSnapshot) -> Product
    fun toModel(id: String, entity: Map<String, Any?>): Product {
        return Product(
            id = id,
            name = entity["name"] as? String ?: "",
            description = entity["description"] as? String ?: "",
            price = (entity["price"] as? Number)?.toDouble() ?: 0.0,
            sellerId = entity["sellerId"] as? String ?: "",
            images = (entity["images"] as? List<*>)?.filterIsInstance<String>(),
            status = entity["status"] as? String ?: "active"
        )
    }

    // Convert Product -> Firestore entity (Map)
    fun toEntity(product: Product): Map<String, Any?> {
        return mapOf(
            "name" to product.name,
            "description" to product.description,
            "price" to product.price,
            "sellerId" to product.sellerId,
            "images" to product.images,
            "status" to product.status
        )
    }
}
