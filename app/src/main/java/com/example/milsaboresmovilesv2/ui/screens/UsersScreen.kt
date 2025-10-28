package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton



//estos son importantes, sirven para el mutablestate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(userVM: UserViewModel) {
    //guarda los datos
    val users = userVM.users.collectAsState().value

    // Usuario seleccionado para eliminar
    var userToDelete by remember { mutableStateOf<User?>(null) }

    // Mostrar diálogo de confirmación
    var showConfirmDialog by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Usuarios Registrados",
                    color = Color(0xFF8B4513),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFFFFBF2)
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(users) { user ->

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // ✅ El texto tiene un weight para dejar espacio al icono
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Nombre: ${user.nombre}")
                            Text("Email: ${user.email}")
                            Text("Usuario: ${user.username}")
                            Text("Nacimiento: ${user.fechaNacimiento}")
                            Text("Código Promo: ${user.codigoPromo ?: "Ninguno"}")
                        }

                        // ✅ Ícono visible SIEMPRE
                        IconButton(
                            onClick = {
                                userToDelete = user
                                showConfirmDialog = true
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar usuario",
                                tint = Color.Red,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        }
        //bloque de confirmacion
        if (showConfirmDialog && userToDelete != null) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },

                confirmButton = {
                    TextButton(
                        onClick = {
                            userVM.deleteUser(userToDelete!!)
                            showConfirmDialog = false
                            userToDelete = null
                        }
                    ) {
                        Text("Eliminar", color = Color.Red)
                    }
                },

                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Cancelar")
                    }
                },

                title = { Text("¿Estás seguro?") },
                text = { Text("Esta acción eliminará al usuario permanentemente.") }
            )
        }

    }

}