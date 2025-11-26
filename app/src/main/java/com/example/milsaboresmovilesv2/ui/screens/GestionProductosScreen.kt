package com.example.milsaboresmovilesv2.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GestionProductosScreen(
    products: List<Product> = getSampleProducts(),
    onBackClick: () -> Unit = {},
    onAddProduct: () -> Unit = {},
    onEditProduct: (String) -> Unit = {},
    onViewStats: () -> Unit = {},
    onToggleStatus: (String, Boolean) -> Unit = { _, _ -> },
    onDeleteProduct: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Tortas", "Postres", "Sin Azúcar", "Especiales")

    val filteredProducts = remember(products, searchText, selectedCategory) {
        products.filter {
            val searchMatch = searchText.isBlank() || it.name.contains(searchText, ignoreCase = true)
            val categoryMatch = selectedCategory == "Todos" || it.category.equals(selectedCategory, ignoreCase = true)
            searchMatch && categoryMatch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Gestión de Productos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar productos...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF8F8F8),
                focusedContainerColor = Color(0xFFF8F8F8),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFE67E22)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filtros de categoría
        Text(
            text = "Categorías:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFE67E22),
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.height(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Estadísticas rápidas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductStatCard(
                modifier = Modifier.weight(1f),
                title = "Total",
                value = products.size.toString(),
                color = Color(0xFF3498DB)
            )
            ProductStatCard(
                modifier = Modifier.weight(1f),
                title = "Disponibles",
                value = products.count { it.inStock }.toString(),
                color = Color(0xFF27AE60)
            )
            ProductStatCard(
                modifier = Modifier.weight(1f),
                title = "Agotados",
                value = products.count { !it.inStock }.toString(),
                color = Color(0xFFE74C3C)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón agregar producto
        Button(
            onClick = onAddProduct,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC0CB),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar Nuevo Producto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de productos
        Text(
            text = "Productos (${filteredProducts.size})",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredProducts) { product ->
                ProductManagementItem(
                    product = product,
                    onEdit = { onEditProduct(product.id) },
                    onToggleStatus = { onToggleStatus(product.id, !product.inStock) },
                    onDelete = { onDeleteProduct(product.id) }
                )
            }
        }
    }
}

@Composable
fun ProductStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
fun ProductManagementItem(
    product: Product,
    onEdit: () -> Unit,
    onToggleStatus: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto (placeholder)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFF8F4E8), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Cake,
                    contentDescription = "Producto",
                    tint = Color(0xFFD35400)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Text(
                    text = product.category,
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
                Text(
                    text = product.price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE67E22)
                )
            }

            // Estado y acciones
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Badge de estado
                Text(
                    text = if (product.inStock) "DISPONIBLE" else "AGOTADO",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (product.inStock) Color(0xFF27AE60) else Color(0xFFE74C3C)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botones de acción
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color(0xFF3498DB)
                        )
                    }

                    IconButton(
                        onClick = onToggleStatus,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            if (product.inStock) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Cambiar estado",
                            tint = Color(0xFF95A5A6)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFE74C3C)
                        )
                    }
                }
            }
        }
    }
}

// Data classes
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: String,
    val inStock: Boolean
)

// Datos de ejemplo
fun getSampleProducts(): List<Product> {
    return listOf(
        Product("1", "Torta Chocolate", "Tortas", "$45.000", true),
        Product("2", "Torta Vainilla", "Tortas", "$40.000", true),
        Product("3", "Mousse Chocolate", "Postres", "$5.000", false),
        Product("4", "Cheesecake", "Postres", "$8.000", true),
        Product("5", "Torta Sin Azúcar", "Sin Azúcar", "$35.000", true),
        Product("6", "Brownie Especial", "Especiales", "$6.000", true)
    )
}

@Preview(showBackground = true)
@Composable
fun GestionProductosScreenPreview() {
    GestionProductosScreen()
}
