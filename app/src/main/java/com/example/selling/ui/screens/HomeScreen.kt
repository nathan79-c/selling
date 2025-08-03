package com.example.selling.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.selling.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String = "Nathan",
    profilePictureUrl: String = "", // mettre une image réelle plus tard
    onProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    productList: List<Product> = sampleProducts
) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MaBoutique", fontWeight = FontWeight.Bold)
                },
                actions = {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.me), // image temporaire
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable { showMenu = !showMenu }
                        )

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Voir profil") },
                                onClick = {
                                    showMenu = false
                                    onProfileClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Se déconnecter") },
                                onClick = {
                                    showMenu = false
                                    onLogoutClick()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productList) { product ->
                    ProductCard(product = product, onClick = { onProductClick(product) })
                }
            }
        }
    )
}


@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.9f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, fontWeight = FontWeight.Bold)
            Text(text = "${product.price} FCFA", color = Color.Gray)
        }
    }
}

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int,
    val description: String = "", // Valeur par défaut
    val status: String = "Disponible" // Valeur par défaut
)


val sampleProducts = listOf(
    Product(1, "Casque Audio", 15000, R.drawable.product1),
    Product(2, "Montre Luxe", 30000, R.drawable.product2),
    Product(3, "Chaussures", 25000, R.drawable.product1),
    Product(4, "Sac à dos", 18000, R.drawable.product2),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val sampleProducts = listOf(
        Product(1, "Casque Audio", 15000, R.drawable.product2),
        Product(2, "Montre Luxe", 30000, R.drawable.product1),
        Product(3, "Chaussures", 25000, R.drawable.product2),
        Product(4, "Sac à dos", 18000, R.drawable.product1),
    )

    HomeScreen(
        userName = "Nathan",
        profilePictureUrl = "",
        onProfileClick = {},
        onLogoutClick = {},
        onProductClick = {},
        productList = sampleProducts
    )
}
