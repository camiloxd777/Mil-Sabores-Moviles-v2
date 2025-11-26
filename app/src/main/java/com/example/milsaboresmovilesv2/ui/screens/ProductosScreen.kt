package com.example.milsaboresmovilesv2.ui.screens

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.ui.utils.getProductImage
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import com.example.milsaboresmovilesv2.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

data class Producto(val nombre: String, val descripcion: String, val precio: String, val imagen: Int)

@Composable
fun ProductosScreen(
    navController: NavController,
    carritoVM: CarritoViewModel,
    productVM: ProductViewModel
) {
    val productos by productVM.products.collectAsState()
    val error by productVM.error.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        productVM.loadProducts()
    }

    val listState = rememberLazyListState()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val categorias = productos.groupBy { it.categoria }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        if (error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error, color = Color.Red)
            }
        } else if (productos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                categorias.forEach { (categoria, productosCategoria) ->
                    item {
                        Text(
                            text = categoria,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF5C3A21),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(productosCategoria) { producto ->
                        val uiProducto = Producto(
                            nombre = producto.nombre,
                            descripcion = producto.descripcion,
                            precio = "${producto.precio}",
                            imagen = getProductImage(producto.nombre, producto.categoria)
                        )
                        ProductoCard(
                            producto = uiProducto,
                            onAgregarClick = {
                                navController.navigate(
                                    "detalleProducto/${
                                        Uri.encode(uiProducto.nombre)
                                    }/${Uri.encode(uiProducto.descripcion)}/${Uri.encode(uiProducto.precio)}/${uiProducto.imagen}"
                                )
                            }
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    containerColor = Color(0xFFFFEAC4),
                    contentColor = Color(0xFF5C3A21),
                ) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = "Subir")
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onAgregarClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(producto.descripcion, fontSize = 13.sp, color = Color.DarkGray)
                Text(
                    "$${producto.precio}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE66B00),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Button(
                onClick = onAgregarClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC6CF)),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Text("Ver", color = Color.White)
            }
        }
    }
}