package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.R
import kotlinx.coroutines.launch

data class Producto(val nombre: String, val imagen: Int)

@Composable
fun ProductosScreen() {
    val categorias = mapOf(
        //PRODUCTOS
        "Tortas Cuadradas" to listOf(
            Producto("Torta cuadrada de chocolate", R.drawable.torta_chocolate),
            Producto("Torta cuadrada de frutas", R.drawable.torta_cuadrada)
        ),
        "Tortas Circulares" to listOf(
            Producto("Torta circular de manjar", R.drawable.torta_manjar_v2),
            Producto("Torta circular de vainilla", R.drawable.torta_vainilla)
        ),
        "Postres Individuales" to listOf(
            Producto("Mousse de chocolate", R.drawable.mousse),
            Producto("Tiramisú clásico", R.drawable.tiramisu)
        ),
        "Productos sin azúcar" to listOf(
            Producto("Torta sin azúcar de naranja", R.drawable.torta_naranja),
            Producto("Cheesecake sin azúcar", R.drawable.cheesecake_frutilla)
        ),
        "Pastelería tradicional" to listOf(
            Producto("Empanada de manzana", R.drawable.empanada_manzana),
            Producto("Tarta de Santiago", R.drawable.tarta_santiago)
        ),
        "Productos sin gluten" to listOf(
            Producto("Brownie sin gluten", R.drawable.brownie),
            Producto("Pan sin gluten", R.drawable.pan_sin_gluten)
        ),
        "Productos veganos" to listOf(
            Producto("Torta vegana de chocolate", R.drawable.chocolate_vegano),
            Producto("Galletas veganas de avena", R.drawable.galleta_vegana)
        ),
        "Tortas especiales" to listOf(
            Producto("Torta especial de cumpleaños", R.drawable.torta_hbd),
            Producto("Torta especial de boda", R.drawable.torta_boda)
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFF5E1))) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)
        ) {
            categorias.forEach { (categoria, productos) ->
                item {
                    Text(
                        text = categoria,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF5C3A21),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(productos) { producto ->
                    ProductoCard(producto)
                }
            }
        }

        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            containerColor = Color(0xFFFFEAC4),
            contentColor = Color(0xFF5C3A21),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowUpward, contentDescription = "Subir")
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp)
            )
            Text(
                text = producto.nombre,
                fontSize = 16.sp,
                color = Color(0xFF5C3A21),
                fontWeight = FontWeight.Medium
            )
        }
    }
}