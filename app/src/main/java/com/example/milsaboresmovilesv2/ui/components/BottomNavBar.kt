package com.example.milsaboresmovilesv2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = Color(0xFFFFEAC4)
    ) {
        val items = listOf("Home", "Productos", "Menu")
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        //iconos de la barra de navegación
                        "Home" -> Icon(Icons.Default.Home, contentDescription = item)
                        "Productos" -> Icon(Icons.Default.ShoppingBag, contentDescription = item)
                        "Menu" -> Icon(Icons.Default.Menu, contentDescription = item)
                    }
                },
                label = { Text(item) },
                selected = selectedItem == item,
                onClick = { onItemSelected(item) }
            )
        }
    }
}