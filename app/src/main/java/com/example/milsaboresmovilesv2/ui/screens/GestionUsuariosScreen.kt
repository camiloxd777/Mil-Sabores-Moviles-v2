package com.example.milsaboresmovilesv2.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel

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
    val filters = listOf("Todos", "Activos", "Inactivos", "Administradores", "Clientes")

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
                text = "Gestión de Usuarios",
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
            placeholder = { Text("Buscar usuarios por nombre o email...") },
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
        // Filtros de usuarios
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
            UserStatCard(
                titulo = "Total",
                valor = "1.245",
                color = Color(0xFF3498DB)
            )
            UserStatCard(
                titulo = "Activos",
                valor = "1.156",
                color = Color(0xFF27AE60)
            )
            UserStatCard(
                titulo = "Nuevos Mes",
                valor = "89",
                color = Color(0xFF9B59B6)
            )
            UserStatCard(
                titulo = "Administradores",
                valor = "3",
                color = Color(0xFFE67E22)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Resumen de actividad
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
                        text = "Actividad del Mes",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "+89 nuevos usuarios",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Crecimiento",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "+7.7%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF27AE60)
                    )
                }

            }

        }


        Spacer(modifier = Modifier.height(20.dp))


        // Lista de usuarios
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

                    onDelete = {onDeleteUser(usuario.id)}

                )

            }

        }

    }

}



@Composable

fun UserStatCard(

    titulo: String,

    valor: String,

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

                text = valor,

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold,

                color = color

            )

            Text(

                text = titulo,

                fontSize = 10.sp,

                color = Color(0xFF666666),

                textAlign = androidx.compose.ui.text.style.TextAlign.Center

            )

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

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            // Avatar del usuario

            Box(

                modifier = Modifier

                    .size(50.dp)

                    .background(

                        if (usuario.rol == "Administrador") Color(0xFFE67E22) else Color(0xFF3498DB),

                        RoundedCornerShape(25.dp)

                    ),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = usuario.nombre.substring(0, 1).uppercase(),

                    color = Color.White,

                    fontSize = 16.sp,

                    fontWeight = FontWeight.Bold

                )

            }



            Spacer(modifier = Modifier.width(16.dp))



            // Información del usuario

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Text(

                        text = usuario.nombre,

                        fontSize = 16.sp,

                        fontWeight = FontWeight.Medium,

                        color = Color(0xFF333333)

                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Badge de rol

                    Box(

                        modifier = Modifier

                            .background(

                                if (usuario.rol == "Administrador") Color(0xFFFFEAA7) else Color(0xFFD6EAF8),

                                RoundedCornerShape(4.dp)

                            )

                            .padding(horizontal = 6.dp, vertical = 2.dp)

                    ) {

                        Text(

                            text = usuario.rol,

                            fontSize = 10.sp,

                            fontWeight = FontWeight.Bold,

                            color = if (usuario.rol == "Administrador") Color(0xFFE67E22) else Color(0xFF3498DB)

                        )

                    }

                }



                Text(

                    text = usuario.email,

                    fontSize = 14.sp,

                    color = Color(0xFF666666)

                )



                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(

                        text = "Registrado: ${usuario.fechaRegistro}",

                        fontSize = 12.sp,

                        color = Color(0xFF888888)

                    )



                    // Estado del usuario

                    Row(

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier

                                .size(8.dp)

                                .background(

                                    if (usuario.activo) Color(0xFF27AE60) else Color(0xFFE74C3C),

                                    RoundedCornerShape(4.dp)

                                )

                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(

                            text = if (usuario.activo) "Activo" else "Inactivo",

                            fontSize = 12.sp,

                            color = if (usuario.activo) Color(0xFF27AE60) else Color(0xFFE74C3C)

                        )

                    }

                }

            }



            Spacer(modifier = Modifier.width(16.dp))



            // Acciones

            Column {

                // Botón de estado

                IconButton(

                    onClick = { onToggleStatus(!usuario.activo) },

                    modifier = Modifier.size(36.dp)

                ) {

                    Icon(

                        if (usuario.activo) Icons.Default.PersonOff else Icons.Default.Person,

                        contentDescription = if (usuario.activo) "Desactivar" else "Activar",

                        tint = if (usuario.activo) Color(0xFFE74C3C) else Color(0xFF27AE60)

                    )

                }



                // Botón editar

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



        // Footer con acciones adicionales

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(horizontal = 16.dp, vertical = 8.dp),

            horizontalArrangement = Arrangement.End

        ) {

            TextButton(onClick = onViewDetails) {

                Text("Ver Detalles")

            }

        }

    }

}



// Data classes en español

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



// Datos de ejemplo en español

fun getUsuariosEjemplo(): List<Usuario> {

    return listOf(

        Usuario(

            id = "1",

            nombre = "María González",

            email = "maria.gonzalez@email.com",

            rol = "Administrador",

            activo = true,

            fechaRegistro = "15/03/2024",

            telefono = "+56 9 1234 5678",

            ultimoAcceso = "Hoy, 14:30"

        ),

        Usuario(

            id = "2",

            nombre = "Carlos López",

            email = "carlos.lopez@email.com",

            rol = "Cliente",

            activo = true,

            fechaRegistro = "20/02/2024",

            telefono = "+56 9 8765 4321",

            ultimoAcceso = "Ayer, 18:45"

        ),

        Usuario(

            id = "3",

            nombre = "Ana Martínez",

            email = "ana.martinez@email.com",

            rol = "Cliente",

            activo = false,

            fechaRegistro = "10/01/2024",

            telefono = "+56 9 5555 6666",

            ultimoAcceso = "05/12/2024"

        ),

        Usuario(

            id = "4",

            nombre = "Pedro Sánchez",

            email = "pedro.sanchez@email.com",

            rol = "Cliente",

            activo = true,

            fechaRegistro = "05/04/2024",

            telefono = "+56 9 7777 8888",

            ultimoAcceso = "Hoy, 09:15"

        ),

        Usuario(

            id = "5",

            nombre = "Laura Rodríguez",

            email = "laura.rodriguez@email.com",

            rol = "Administrador",

            activo = true,

            fechaRegistro = "12/03/2024",

            telefono = "+56 9 9999 0000",

            ultimoAcceso = "Hoy, 11:20"

        )

    )

}



/*@Preview(showBackground = true)

@Composable

fun GestionUsuariosScreenPreview() {

    GestionUsuariosScreen(usuarios = getUsuariosEjemplo())

}*/