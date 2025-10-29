package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.example.milsaboresmovilesv2.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.InvalidationTracker
import com.example.milsaboresmovilesv2.data.local.AppDatabase
import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.local.UserDao
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import kotlinx.coroutines.flow.flowOf
import kotlin.collections.emptyList


@Composable
fun ScreenPrincipal(carritoVM: CarritoViewModel, navController: NavController, userVM: UserViewModel) {
    var selectedItem by remember { mutableStateOf("Home") }
    val navController = rememberNavController()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E0)),
        contentAlignment = Alignment.Center
    ) {

        when (selectedItem) {
            "Home" -> HomeContent(
                onVerProductosClick = { selectedItem = "Productos" },carritoVM=carritoVM
            )
            "Productos" -> ProductosScreen(navController, carritoVM)
            "Menu" -> MenuScreen(navController, userVM)
            "Login" -> LoginScreen(
                userVM=userVM,
                onBackClick = { selectedItem = "Menu" },
                onLogInSuccess = { selectedItem = "Home" })

        }
    }
}

// Home
@Composable
fun HomeContent(onVerProductosClick: () -> Unit = {}, carritoVM: CarritoViewModel) {
    var contentLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {
        // BANNER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.torta_banner),
                    contentDescription = "Fondo pastelería",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "50 Años Endulzando Vidas",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Desde 1974, creando momentos dulces e inolvidables",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = onVerProductosClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD35400),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(48.dp)
                            .width(180.dp)
                    ) {
                        Text(
                            text = "Ver Productos",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Barra de búsqueda
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Categorías
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Categorías",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Lista de categorías
        items(getCategorias()) { categoria ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                CategoriaItem(categoria = categoria)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // PRODUCTOS DESTACADOS
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Productos Destacados",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Lista de productos destacados
        items(getProductosDestacados()) { producto ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                ProductoDestacadoItem(producto = producto, carritoVM = carritoVM)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // DESCUENTOS ESPECIALES
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Descuentos Especiales",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Lista de descuentos especiales
        items(getDescuentosEspeciales()) { especial ->
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                DescuentoEspecialItem(especial = especial)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // NUESTRA HISTORIA
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Nuestra Historia",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = Color(0xFFFFC0CB)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Desde 1974, Pastelería Mil Sabores ha sido un referente en la repostería chilena. En 1995, participamos en el récord Guinness de la torta más grande del mundo.",
                            fontSize = 16.sp,
                            color = Color(0xFF666666),
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SearchBar(){
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            Text(
                text = "Buscar tortas, postres...",
                color = Color(0xFF888888)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF8F8F8),
            focusedContainerColor = Color(0xFFF8F8F8),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFFE67E22),
            cursorColor = Color(0xFFE67E22)
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color(0xFF888888)
            )
        }
    )
}

@Composable
fun CategoriaItem(categoria: Categoria) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = categoria.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "${categoria.cantidadProductos} productos",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardDoubleArrowRight,
                contentDescription = "Ver más",
                tint = Color(0xFF888888)
            )
        }
    }
}

@Composable
fun ProductoDestacadoItem(producto: Producto, carritoVM: CarritoViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                Text(
                    producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )
                Text(
                    producto.descripcion,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 18.sp
                )
                Text(
                    "$${producto.precio}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE66B00),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Button(
                onClick = { carritoVM.add(nombre=producto.nombre, descripcion = producto.descripcion,
                    imagen = producto.imagen, precioTexto = producto.precio) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC6CF)),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Text("+ Agregar", color = Color.White)
            }
        }
    }
}

@Composable
fun DescuentoEspecialItem(especial: DescuentoEspecial) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = especial.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = especial.descripcion,
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}

data class Categoria(
    val nombre: String,
    val cantidadProductos: Int
)

data class DescuentoEspecial(
    val titulo: String,
    val descripcion: String,
    val codigo: String = ""
)

fun getCategorias(): List<Categoria> {
    return listOf(
        Categoria("Tortas Cuadradas", 8),
        Categoria("Tortas Circulares", 6),
        Categoria("Postres Individuales", 12),
        Categoria("Sin Azúcar", 4)
    )
}

// Funciones para obtener los datos
fun getProductosDestacados(): List<Producto> {
    return listOf(
        Producto(
            "Torta Cuadrada de Chocolate",
            "Deliciosa torta con capas de ganache y avellanas",
            "45.000",
            R.drawable.torta_chocolate
        ),
        Producto(
            "Torta Circular de Vainilla",
            "Bizcocho clásico con crema pastelera y glaseado",
            "40.000",
            R.drawable.torta_vainilla
        ),
        Producto(
            "Mousse de Chocolate",
            "Postre individual cremoso con chocolate de alta calidad",
            "5.000",
            R.drawable.mousse
        )
    )
}

fun getDescuentosEspeciales(): List<DescuentoEspecial> {
    return listOf(
        DescuentoEspecial("50+ Años", "50% descuento en todos los productos"),
        DescuentoEspecial("Estudiantes Duoc", "Torta gratis en tu cumpleaños"),
        DescuentoEspecial("Código FELICES50", "10% descuento de por vida", "FELICES50")
    )
}

