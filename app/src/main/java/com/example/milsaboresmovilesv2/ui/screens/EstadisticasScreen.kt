package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasScreen(
    onBackClick: () -> Unit = {},
    onExportReport: (String) -> Unit = {},
    onViewDetailedStats: (String) -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf("Este Mes") }
    val periods = listOf("Hoy", "Esta Semana", "Este Mes", "Este Año")
    var selectedReport by remember { mutableStateOf("Ventas") }
    val reports = listOf("Ventas", "Productos", "Clientes", "Pedidos")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Estadísticas y Reportes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )

            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Selectores de período y reporte
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Selector de período
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Período",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.height(4.dp))
                var expandedPeriod by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedPeriod,
                    onExpandedChange = { expandedPeriod = !expandedPeriod }
                ) {
                    TextField(
                        value = selectedPeriod,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPeriod) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedPeriod,
                        onDismissRequest = { expandedPeriod = false }
                    ) {
                        periods.forEach { period ->
                            DropdownMenuItem(
                                text = { Text(period) },
                                onClick = {
                                    selectedPeriod = period
                                    expandedPeriod = false
                                }
                            )
                        }
                    }
                }
            }

            // Selector de reporte
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Reporte",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.height(4.dp))
                var expandedReport by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedReport,
                    onExpandedChange = { expandedReport = !expandedReport }
                ) {
                    TextField(
                        value = selectedReport,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedReport) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedReport,
                        onDismissRequest = { expandedReport = false }
                    ) {
                        reports.forEach { report ->
                            DropdownMenuItem(
                                text = { Text(report) },
                                onClick = {
                                    selectedReport = report
                                    expandedReport = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Resumen general
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resumen General - $selectedPeriod",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Ingresos",
                        value = "$8.5M",
                        change = "+12%",
                        isPositive = true,
                        color = Color(0xFF27AE60),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Pedidos",
                        value = "245",
                        change = "+8%",
                        isPositive = true,
                        color = Color(0xFF3498DB),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Clientes",
                        value = "156",
                        change = "+15%",
                        isPositive = true,
                        color = Color(0xFF9B59B6),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Promedio",
                        value = "$34.7K",
                        change = "+5%",
                        isPositive = true,
                        color = Color(0xFFE67E22),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Productos más vendidos
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos Más Vendidos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                    TextButton(onClick = { onViewDetailedStats("productos") }) {
                        Text("Ver Todo")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                getTopProducts().forEachIndexed { index, product ->
                    TopProductItem(
                        product = product,
                        rank = index + 1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tendencias de ventas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tendencias de Ventas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                    TextButton(onClick = { onViewDetailedStats("ventas") }) {
                        Text("Ver Gráfico")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Gráfico simple (placeholder)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color(0xFFF8F9FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gráfico de Ventas Semanales\n(Lunes - Domingo)",
                        textAlign = TextAlign.Center,
                        color = Color(0xFF666666)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Lun: $1.2M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Mar: $1.4M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Mié: $1.1M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Jue: $1.6M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Vie: $2.1M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Sáb: $0.9M", fontSize = 12.sp, color = Color(0xFF666666))
                    Text("Dom: $0.8M", fontSize = 12.sp, color = Color(0xFF666666))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Acciones de exportación
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4E8)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Exportar Reportes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onExportReport("pdf") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE74C3C)
                        )
                    ) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("PDF")
                    }

                    Button(
                        onClick = { onExportReport("excel") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF27AE60)
                        )
                    ) {
                        Icon(Icons.Default.TableChart, contentDescription = "Excel")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Excel")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = change,
                fontSize = 10.sp,
                color = if (isPositive) Color(0xFF27AE60) else Color(0xFFE74C3C),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TopProductItem(
    product: TopProduct,
    rank: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ranking
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = Color(0xFFE67E22), shape = androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Información del producto
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )
            Text(
                text = "${product.sales} ventas • ${product.revenue}",
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
        }

        // Porcentaje
        Text(
            text = "${product.growth}%",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (product.growth > 0) Color(0xFF27AE60) else Color(0xFFE74C3C)
        )
    }
}

// Data classes
data class TopProduct(
    val name: String,
    val sales: Int,
    val revenue: String,
    val growth: Double
)

// Datos de ejemplo
fun getTopProducts(): List<TopProduct> {
    return listOf(
        TopProduct("Torta Chocolate", 45, "$2.025.000", 15.2),
        TopProduct("Torta Vainilla", 38, "$1.520.000", 8.7),
        TopProduct("Cheesecake", 32, "$256.000", 22.4),
        TopProduct("Mousse Chocolate", 28, "$140.000", 5.3),
        TopProduct("Torta Sin Azúcar", 25, "$875.000", 12.8)
    )
}

@Preview(showBackground = true)
@Composable
fun EstadisticasScreenPreview() {
    EstadisticasScreen()
}