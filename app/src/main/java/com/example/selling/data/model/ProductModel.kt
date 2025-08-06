package com.example.selling.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int,
    val description: String = "", // Valeur par défaut
    val status: String = "Disponible" // Valeur par défaut
)

object ProductMapper {

    // Convert ENtity to Model
    fun toModel(){

    }

    fun toEntity(){

    }
}