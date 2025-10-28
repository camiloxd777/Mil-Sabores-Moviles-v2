package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel

@Composable
fun BdUsersScreen(userVM: UserViewModel) {

    val users = userVM.users.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .padding(16.dp)
    ) {
        Text(
            text = "Base de Datos - Usuarios",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF8B4513)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(users) { user ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nombre: ${user.nombre}")
                        Text("Email: ${user.email}")
                        Text("Usuario: ${user.username}")
                        Text("Nacimiento: ${user.fechaNacimiento}")
                        Text("Código Promo: ${user.codigoPromo ?: "Ninguno"}")
                    }
                }
            }
        }
    }
}
