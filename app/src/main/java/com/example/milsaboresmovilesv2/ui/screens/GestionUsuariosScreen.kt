package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
fun GestionUsuariosScreen(
    usuarios: List<Usuario>,
    onBackClick: () -> Unit = {},
    onViewUserDetails: (String) -> Unit = {},
    onEditUser: (String) -> Unit = {},
    onToggleUserStatus: (String, Boolean) -> Unit = { _, _ -> },
    onDeleteUser: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    val filters = listOf("Todos", "Activos", "Inactivos")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Gestión de Usuarios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(48.dp)) // For balance
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Busqueda y filtros
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar usuarios por nombre o email...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF8F8F8),
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFE67E22), selectedLabelColor = Color.White),
                    modifier = Modifier.height(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Estadisticas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserStatCard(
                modifier = Modifier.weight(1f),
                titulo = "Total",
                valor = usuarios.size.toString(),
                color = Color(0xFF3498DB)
            )
            UserStatCard(
                modifier = Modifier.weight(1f),
                titulo = "Activos",
                valor = usuarios.count { it.activo }.toString(),
                color = Color(0xFF27AE60)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Lista de usuarios
        Text(
            text = "Usuarios Registrados",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(usuarios) { usuario ->
                UsuarioManagementItem(
                    usuario = usuario,
                    onViewDetails = { onViewUserDetails(usuario.id) },
                    onEdit = { onEditUser(usuario.id) },
                    onToggleStatus = { activo -> onToggleUserStatus(usuario.id, activo) },
                    onDelete = { onDeleteUser(usuario.id) }
                )
            }
        }
    }
}

@Composable
fun UserStatCard(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: String,
    color: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = valor, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = titulo, fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun UsuarioManagementItem(
    usuario: Usuario,
    onViewDetails: () -> Unit,
    onEdit: () -> Unit,
    onToggleStatus: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (usuario.rol == "Administrador") Color(0xFFE67E22) else Color(0xFF3498DB),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = usuario.nombre.take(1).uppercase(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = usuario.nombre, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text(text = usuario.email, fontSize = 14.sp, color = Color.Gray)
                }

                Box(
                    modifier = Modifier
                        .background(
                            if (usuario.rol == "Administrador") Color(0xFFFFEAA7) else Color(0xFFD6EAF8),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = usuario.rol, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (usuario.rol == "Administrador") Color(0xFFE67E22) else Color(0xFF3498DB))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(if (usuario.activo) Color(0xFF27AE60) else Color(0xFFE74C3C), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (usuario.activo) "Activo" else "Inactivo", fontSize = 12.sp, color = if (usuario.activo) Color(0xFF27AE60) else Color(0xFFE74C3C))
                }

                Row {
                    TextButton(onClick = onViewDetails) { Text("Detalles") }
                    IconButton(onClick = { onToggleStatus(!usuario.activo) }) {
                        Icon(if (usuario.activo) Icons.Default.PersonOff else Icons.Default.Person, contentDescription = "Toggle Status")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}

data class Usuario(
    val id: String,
    val nombre: String,
    val email: String,
    val rol: String,
    val activo: Boolean,
    val fechaRegistro: String,
    val telefono: String,
    val ultimoAcceso: String
)

fun getUsuariosEjemplo(): List<Usuario> {
    return listOf(
        Usuario("1", "María González", "maria.gonzalez@email.com", "Administrador", true, "15/03/2024", "+56912345678", "Hoy, 14:30"),
        Usuario("2", "Carlos López", "carlos.lopez@email.com", "Cliente", true, "20/02/2024", "+56987654321", "Ayer, 18:45"),
        Usuario("3", "Ana Martínez", "ana.martinez@email.com", "Cliente", false, "10/01/2024", "+56955556666", "05/12/2024"),
        Usuario("4", "Pedro Sánchez", "pedro.sanchez@email.com", "Cliente", true, "05/04/2024", "+56977778888", "Hoy, 09:15"),
        Usuario("5", "Laura Rodríguez", "laura.rodriguez@email.com", "Administrador", true, "12/03/2024", "+56999990000", "Hoy, 11:20")
    )
}

@Preview(showBackground = true)
@Composable
fun GestionUsuariosScreenPreview() {
    GestionUsuariosScreen(usuarios = getUsuariosEjemplo())
}
