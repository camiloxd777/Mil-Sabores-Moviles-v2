package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import org.w3c.dom.Text


@Composable
fun MenuScreen(navController: NavController, userVM: UserViewModel){
    var selectedItem by remember { mutableStateOf("Menu") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {
        MenuContent(userVM=userVM,onLoginClick = {navController.navigate("login")}, onVerProductos = {navController.navigate("productos")})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(onLoginClick: () -> Unit = {}) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Menú",
                    color = Color(0xFF8B4513),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            Button(
                onClick = {onLoginClick()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD35400),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFFBF2)
        )
    )
}

@Composable
fun MenuContent(userVM: UserViewModel,onLoginClick: () -> Unit = {}, onVerProductos: ()-> Unit={}) {

    val loggedUser = userVM.loginState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        if (loggedUser != null) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFE7FF)),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = Color(0xFF8B4513),
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = loggedUser.username,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = loggedUser.email,
                        fontSize = 16.sp,
                        color = Color(0xFF555555),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { userVM.logout() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD35400),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp)
                    ) {
                        Text("Cerrar Sesión")
                    }
                }
            }

        } else {
            //si no hay sesión muestra la tarjeta del login
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC0CB)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C3E50)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Inicia sesión para acceder a todas las funciones",
                        fontSize = 16.sp,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { onLoginClick() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8B4513),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Text(
            text = "Opciones",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getOpcionesMenu()) { opcion ->
                OpcionMenuItem(opcion = opcion)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4E8)),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ContactoItem(icon = Icons.Default.Phone, text = "+56 9 12345678")
                ContactoItem(icon = Icons.Default.Email, text = "info@milsabores.cl")
                ContactoItem(icon = Icons.Default.LocationOn, text = "Av. Principal 123, Santiago")
                ContactoItem(icon = Icons.Default.Schedule, text = "Lun-Vie: 9:00 - 20:00")
            }
        }
    }
}

@Composable
fun OpcionMenuItem(opcion: OpcionMenu) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = opcion.icono,
                contentDescription = opcion.titulo,
                tint = Color(0xFFD35400),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = opcion.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                if (opcion.descripcion.isNotEmpty()) {
                    Text(
                        text = opcion.descripcion,
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Ver más",
                tint = Color(0xFF888888)
            )
        }
    }
}

@Composable
fun ContactoItem(icon: ImageVector, text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF666666),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
    }
}

data class OpcionMenu(
    val titulo: String,
    val descripcion: String = "",
    val icono: ImageVector
)

fun getOpcionesMenu(): List<OpcionMenu> {
    return listOf(
        OpcionMenu(
            "Mis Pedidos",
            "Revisa el historial de tus pedidos",
            Icons.Default.ShoppingBag
        ),
        OpcionMenu(
            "Mis Favoritos",
            "Tus productos favoritos guardados",
            Icons.Default.Favorite
        ),
        OpcionMenu(
            "Direcciones",
            "Gestiona tus direcciones de entrega",
            Icons.Default.Home
        ),
        OpcionMenu(
            "Método de Pago",
            "Tarjetas y formas de pago",
            Icons.Default.CreditCard
        ),
        OpcionMenu(
            "Cofiguración",
            "Ajustes de la aplicación",
            Icons.Default.Settings
        ),
        OpcionMenu(
            "Ayuda y Soporte",
            "Centro de ayuda y contacto",
            Icons.Default.Help
        )

    )
}

/*Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen()
}*/