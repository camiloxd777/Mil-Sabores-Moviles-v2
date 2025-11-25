package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.R
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel

@Composable
fun MisFavoritosScreen(navController: NavController, userVM: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {
        val favoritos = listOf(
            Producto("Torta de Chocolate", "Deliciosa torta de chocolate", "$45.000", R.drawable.torta_chocolate),
            Producto("Mousse", "Postre individual", "$5.000", R.drawable.mousse)
        )

        if (favoritos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Sin favoritos",
                        tint = Color(0xFFCCCCCC),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No tienes productos favoritos",
                        fontSize = 18.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
        } else {
            Text(
                text = "Mis Favoritos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos) { producto ->
                    ProductoCard(
                        producto = producto,
                        onAgregarClick = {

                        }
                    )
                }
            }
        }
    }
}