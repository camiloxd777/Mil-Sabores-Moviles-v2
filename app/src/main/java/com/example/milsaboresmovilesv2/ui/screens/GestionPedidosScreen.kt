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
fun GestionPedidosScreen(
    onBackClick: () -> Unit = {},
    onViewOrderDetails: (String) -> Unit = {},
    onUpdateOrderStatus: (String, String) -> Unit = {orderId, newStatus -> }
) {
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    val filters = listOf("Todos", "Pendientes", "En Proceso", "Completados", "Cancelados")

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
            Text(
                text = "Gestión de Pedidos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )

            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar pedidos...") },
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

        // Filtros de estado
        Text(
            text = "Filtrar por:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
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
            OrderStatCard(
                title = "Hoy",
                value = "24",
                color = Color(0xFF3498DB)
            )
            OrderStatCard(
                title = "Pendientes",
                value = "12",
                color = Color(0xFFF39C12)
            )
            OrderStatCard(
                title = "Completados",
                value = "8",
                color = Color(0xFF27AE60)
            )
            OrderStatCard(
                title = "Cancelados",
                value = "4",
                color = Color(0xFFE74C3C)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Resumen del día
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Ingresos del día",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "$1.250.000",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Pedidos promedio",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "$52.083",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF27AE60)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lista de pedidos
        Text(
            text = "Pedidos Recientes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getSampleOrders()) { order ->
                OrderManagementItem(
                    order = order,
                    onViewDetails = { onViewOrderDetails(order.id) },
                    onUpdateStatus = { newStatus -> onUpdateOrderStatus(order.id, newStatus) }
                )
            }
        }
    }
}

@Composable
fun OrderStatCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OrderManagementItem(
    order: Order,
    onViewDetails: () -> Unit,
    onUpdateStatus: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header del pedido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pedido #${order.id}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = order.customerName,
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }

                // Badge de estado
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = order.status.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = getStatusColor(order.status),
                        modifier = Modifier
                            .background(getStatusBackgroundColor(order.status), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Detalles del pedido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Productos",
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "${order.items.size} items",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total",
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = order.totalAmount,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE67E22)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Información de entrega
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Fecha",
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = order.orderDate,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Entrega",
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = order.deliveryTime,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón ver detalles
                OutlinedButton(
                    onClick = onViewDetails,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF3498DB)
                    )
                ) {
                    Text("Ver Detalles")
                }

                // Selector de estado
                var expanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFE67E22)
                        )
                    ) {
                        Text("Cambiar Estado")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Pendiente", "En Proceso", "Completado", "Cancelado").forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    onUpdateStatus(status)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Funciones auxiliares para colores de estado
fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "pendiente" -> Color(0xFFF39C12)
        "en proceso" -> Color(0xFF3498DB)
        "completado" -> Color(0xFF27AE60)
        "cancelado" -> Color(0xFFE74C3C)
        else -> Color(0xFF666666)
    }
}

fun getStatusBackgroundColor(status: String): Color {
    return when (status.lowercase()) {
        "pendiente" -> Color(0xFFFFF5E6)
        "en proceso" -> Color(0xFFE8F4FD)
        "completado" -> Color(0xFFE8F6EF)
        "cancelado" -> Color(0xFFFDEDEC)
        else -> Color(0xFFF8F8F8)
    }
}

// Data classes
data class Order(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val items: List<OrderItem>,
    val totalAmount: String,
    val status: String,
    val orderDate: String,
    val deliveryTime: String,
    val address: String
)

data class OrderItem(
    val productName: String,
    val quantity: Int,
    val price: String
)

// Datos de ejemplo
fun getSampleOrders(): List<Order> {
    return listOf(
        Order(
            id = "1001",
            customerName = "María González",
            customerPhone = "+56 9 1234 5678",
            items = listOf(
                OrderItem("Torta Chocolate", 1, "$45.000"),
                OrderItem("Mousse Chocolate", 2, "$10.000")
            ),
            totalAmount = "$55.000",
            status = "Pendiente",
            orderDate = "15 Dic 2024",
            deliveryTime = "16:00 - 17:00",
            address = "Av. Principal 123"
        ),
        Order(
            id = "1002",
            customerName = "Carlos López",
            customerPhone = "+56 9 8765 4321",
            items = listOf(
                OrderItem("Torta Vainilla", 1, "$40.000"),
                OrderItem("Cheesecake", 1, "$8.000")
            ),
            totalAmount = "$48.000",
            status = "En Proceso",
            orderDate = "15 Dic 2024",
            deliveryTime = "14:00 - 15:00",
            address = "Calle Secundaria 456"
        ),
        Order(
            id = "1003",
            customerName = "Ana Martínez",
            customerPhone = "+56 9 5555 6666",
            items = listOf(
                OrderItem("Torta Sin Azúcar", 1, "$35.000"),
                OrderItem("Brownie Especial", 3, "$18.000")
            ),
            totalAmount = "$53.000",
            status = "Completado",
            orderDate = "14 Dic 2024",
            deliveryTime = "Entregado",
            address = "Plaza Central 789"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GestionPedidosScreenPreview() {
    GestionPedidosScreen()
}