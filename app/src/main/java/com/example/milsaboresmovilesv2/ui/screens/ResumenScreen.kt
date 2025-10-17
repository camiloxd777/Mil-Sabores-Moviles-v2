package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
){
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Text(text = "Resumen del registro", style= MaterialTheme.typography.headlineMedium)
        Text(text = "Nombre: ${estado.nombre}")
        Text(text = "Correo: ${estado.correo}")
        Text(text = "Direccion: ${estado.direccion}")
        Text(text = "Contraseña: ${estado.clave}")
        Text(text = "Terminos: ${if(estado.aceptaTerminos) "Aceptados" else "No aceptados"}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (viewModel.validarFormulario()){
                    navController.navigate(route = "registro")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver")
        }
    }
}