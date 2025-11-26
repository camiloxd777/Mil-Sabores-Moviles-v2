package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminProfileScreen(
    onBackClick: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onManageProducts: () -> Unit = {},
    onViewOrders: () -> Unit = {},
    onViewStatistics: () -> Unit = {},
    onManageUsers: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .verticalScroll(rememberScrollState())
    ) {
        /** HEADER **/
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF0DA)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Panel Administrador",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2C3E50),
                        textAlign = TextAlign.Center
                    )
                }

                /** AVATAR **/
                Box(contentAlignment = Alignment.BottomEnd) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Admin Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF8B4513))
                            .padding(16.dp),
                        tint = Color.White
                    )

                    // Badge ADMIN
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF8B4513)
                        ),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "ADMIN",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Administrador Principal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = "admin@milsabores.cl",
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )

                Spacer(modifier = Modifier.height(20.dp))

                /** BOTONES HEADER **/
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onEditProfile,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC0CB),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Editar Perfil")
                    }

                    Button(
                        onClick = { expanded = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF95A5A6),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Más Opciones")
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configuración Avanzada") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Backup de Datos") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Soporte Técnico") },
                        onClick = { expanded = false }
                    )
                }
            }
        }

        /** ESTADÍSTICAS **/
        Text(
            text = "Estadísticas Rápidas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("Pedidos Hoy", "24", Color(0xFF27AE60), modifier = Modifier.weight(1f))
            StatCard("Productos", "100", Color(0xFF3498DB), modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("Usuarios", "1K", Color(0xFF9B59B6), modifier = Modifier.weight(1f))
            StatCard("Ingresos Mes", "$1M", Color(0xFFE67E22), modifier = Modifier.weight(1f))
        }

        /** PANEL ADMIN **/
        Text(
            text = "Panel de Control",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            getAdminOptions().forEach { option ->
                AdminOptionItem(option = option) {
                    when (option.title) {
                        "Gestión de Productos" -> onManageProducts()
                        "Gestión de Pedidos" -> onViewOrders()
                        "Estadísticas y Reportes" -> onViewStatistics()
                        "Gestión de Usuarios" -> onManageUsers()
                    }
                }
            }
        }

        /** ACCIONES CRÍTICAS **/
        Text(
            text = "Acciones Críticas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { /* Exportar datos */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF39C12),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Exportar Datos")
                    }

                    Button(
                        onClick = onLogout,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE74C3C),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cerrar Sesión")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Último acceso: Hoy, 14:30",
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

/** COMPONENTES **/

@Composable
fun StatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
            Text(title, fontSize = 12.sp, color = Color(0xFF666666))
        }
    }
}

@Composable
fun AdminOptionItem(option: AdminOption, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = option.icon,
                contentDescription = option.title,
                tint = option.color,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(option.title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(option.description, fontSize = 12.sp, color = Color(0xFF666666))
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF888888)
            )
        }
    }
}

/** MODELO **/
data class AdminOption(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

/** LISTA DE OPCIONES **/
fun getAdminOptions(): List<AdminOption> {
    return listOf(
        AdminOption(
            "Gestión de Productos",
            "Agregar, editar y eliminar productos",
            Icons.Default.ShoppingBag,
            Color(0xFF27AE60)
        ),
        AdminOption(
            "Gestión de Pedidos",
            "Ver y gestionar todos los pedidos",
            Icons.Default.ShoppingCart,
            Color(0xFF3498DB)
        ),
        AdminOption(
            "Estadísticas y Reportes",
            "Ver reportes de ventas y análisis",
            Icons.Default.BarChart,
            Color(0xFF9B59B6)
        ),
        AdminOption(
            "Gestión de Usuarios",
            "Administrar usuarios y permisos",
            Icons.Default.People,
            Color(0xFFE67E22)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AdminProfileScreenPreview() {
    AdminProfileScreen()
}

