package com.example.milsaboresmovilesv2.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import com.example.milsaboresmovilesv2.utils.openCamera
import kotlinx.coroutines.launch

@Composable
fun DetalleProductoScreen(
    nombre: String,
    descripcion: String,
    precio: String,
    imagen: Int,
    carritoVM: CarritoViewModel,
    onIrCarrito: () -> Unit,
    navController: NavController
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var permisoCamaraConcedido by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(16.dp)
    ) {

        // flecha volver
        IconButton(onClick = {
            navController.navigate("productos") {
                popUpTo("productos") { inclusive = false }
                launchSingleTop = true
            }
        }) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF5C3A21)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = imagen),
            contentDescription = nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // contenido del producto
        Text(
            text = nombre,
            fontSize = 22.sp,
            color = Color(0xFF3E2723),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = descripcion,
            fontSize = 15.sp,
            color = Color(0xFF6D4C41)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "$$precio",
            fontSize = 22.sp,
            color = Color(0xFFE66B00),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // boton agregar al carrito
        Button(
            onClick = {
                carritoVM.add(
                    nombre = nombre,
                    descripcion = descripcion,
                    imagen = imagen,
                    precioTexto = precio
                )
                onIrCarrito()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp)
                .width(220.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD35400))
        ) {
            Text("Agregar al carrito", color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "¿Tienes algún descuento presencial? ¡Escanea!",
            color = Color(0xFF5C3A21),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                openCamera(context)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD35400),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp)
                .width(200.dp)
        ) {
            Text("Escanear código")
        }
    }
}