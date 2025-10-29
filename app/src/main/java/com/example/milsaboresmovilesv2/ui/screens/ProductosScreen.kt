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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.milsaboresmovilesv2.R
import com.example.milsaboresmovilesv2.navigation.AppNavigation
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch

data class Producto(val nombre: String,val descripcion: String, val precio: String,  val imagen: Int)

@Composable
fun ProductosScreen(navController: NavController, carritoVM: CarritoViewModel) {
    val categorias = mapOf(
        //PRODUCTOS
        "Tortas Cuadradas" to listOf(
            Producto("Torta cuadrada de chocolate","Deliciosa torta de chocolate con capas de ganache y avellanas.","45.000", R.drawable.torta_chocolate),
            Producto("Torta cuadrada de frutas","Exquisita torta con frutas frescas bañada en gelatina brillante." ,"50.000",R.drawable.torta_cuadrada)
        ),
        "Tortas Circulares" to listOf(
            Producto("Torta circular de manjar","Bizcocho de vainilla relleno con manjar y con crema chantillí.","40.000", R.drawable.torta_manjar_v2),
            Producto("Torta circular de vainilla","Bizcocho de vainilla relleno con crema pastelera y un glaseado dulce.","42.000", R.drawable.torta_vainilla)
        ),
        "Postres Individuales" to listOf(
            Producto("Mousse de chocolate","Postre individual hecho con chocolate de alta calidad.","5.000", R.drawable.mousse),
            Producto("Tiramisú clásico","Postre italiano con capas de bizcocho de soletilla, café, crema y cacao.","5.500", R.drawable.tiramisu)
        ),
        "Productos sin azúcar" to listOf(
            Producto("Torta sin azúcar de naranja","Torta de naranja endulzada naturalmente","48.000", R.drawable.torta_naranja),
            Producto("Cheesecake sin azúcar","Cheesecake con base de nueces, endulzado con stevia.","47.000", R.drawable.cheesecake_frutilla)
        ),
        "Pastelería tradicional" to listOf(
            Producto("Empanada de manzana","Empanada de hojaldre rellena de manzana canela.","3.000", R.drawable.empanada_manzana),
            Producto("Tarta de Santiago","Tarta tradicional de almendras.","6.000", R.drawable.tarta_santiago)
        ),
        "Productos sin gluten" to listOf(
            Producto("Brownie sin gluten","Brownie de chocolate, elaborado con harina de almendras.","4.000", R.drawable.brownie),
            Producto("Pan sin gluten","Pan elaborado con harina de arroz y tapioca.","3.500", R.drawable.pan_sin_gluten)
        ),
        "Productos veganos" to listOf(
            Producto("Torta vegana de chocolate","Torta de chocolate sin ingredientes de origen animal.","50.000", R.drawable.chocolate_vegano),
            Producto("Galletas veganas de avena","Galletas de avena con pasas y canela. Sin lácteos ni huevos.","4.500", R.drawable.galleta_vegana)
        ),
        "Tortas especiales" to listOf(
            Producto("Torta especial de cumpleaños","Torta decorada para celebraciones especiales, personalizable.","55.000", R.drawable.torta_hbd),
            Producto("Torta especial de boda","Elegante torta nupcial de varios pisos.","60.000", R.drawable.torta_boda)
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val showButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFF5E1))) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)
        ) {
            categorias.forEach { (categoria, productos) ->
                item {
                    Text(
                        text = categoria,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF5C3A21),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                items(productos) { producto -> //agrega productos
                    ProductoCard(producto){
                        navController.navigate( //navega al carrito
                            "detalleProducto/${Uri.encode(producto.nombre)}/${Uri.encode(producto.descripcion)}/${Uri.encode(producto.precio)}/${producto.imagen}"
                        )
                    }
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

@Composable
fun ProductoCard(producto: Producto, onAgregarClick:()-> Unit) {
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
            Column (modifier = Modifier.weight(1f)){
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