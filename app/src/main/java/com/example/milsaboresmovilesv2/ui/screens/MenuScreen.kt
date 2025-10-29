package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.utils.openEmail
import com.example.milsaboresmovilesv2.utils.openMaps
import com.example.milsaboresmovilesv2.utils.openPhoneDialer
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel


@Composable
fun MenuScreen(navController: NavController, userVM: UserViewModel){
    var selectedItem by remember { mutableStateOf("Menu") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {
        MenuContent(
            userVM = userVM,
            onLoginClick = { navController.navigate("login") },
            onOpcionMenuClick = { destino ->
                when(destino) {
                    "Mis Pedidos" -> navController.navigate("misPedidos")
                    "Mis Favoritos" -> navController.navigate("misFavoritos")
                    "Direcciones" -> navController.navigate("direcciones")
                    "Método de Pago" -> navController.navigate("metodoPago")
                    "Configuración" -> navController.navigate("configuracion")
                    "Ayuda y Soporte" -> navController.navigate("ayuda")
                }
            }
        )
    }
}

@Composable
fun MenuContent(
    userVM: UserViewModel,
    onLoginClick: () -> Unit = {},
    onOpcionMenuClick: (String) -> Unit = {}
) {
    val loggedUser = userVM.loginState.collectAsState().value
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (loggedUser != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC0CB)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                        color = Color(0xFF2C2C2C),
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
            // Si no hay sesión muestra la tarjeta del login
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
                        onClick = onLoginClick,
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Opciones",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            getOpcionesMenu().forEach { opcion ->
                OpcionMenuItem(
                    opcion = opcion,
                    onClick = {
                        onOpcionMenuClick(opcion.titulo)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4E8)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ContactoItem(
                    icon = Icons.Default.Phone,
                    text = "+56 9 12345678",
                    onClick = {
                        openPhoneDialer(context, "+56912345678")
                    }
                )

                ContactoItem(
                    icon = Icons.Default.Email,
                    text = "info@milsabores.cl",
                    onClick = {
                        openEmail(context, "info@milsabores.cl")
                    }
                )

                ContactoItem(
                    icon = Icons.Default.LocationOn,
                    text = "Av. Principal 123, Santiago",
                    onClick = {
                        openMaps(context, "Av. Principal 123, Santiago")
                    }
                )

                ContactoItem(
                    icon = Icons.Default.Schedule,
                    text = "Lun-Vie: 9:00 - 20:00"
                )
            }
        }
    }
}

@Composable
fun OpcionMenuItem(opcion: OpcionMenu, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
fun ContactoItem(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    val modifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    } else {
        Modifier.fillMaxWidth()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF7D5260),
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF7D5260),
            modifier = Modifier.weight(1f)
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
            titulo = "Mis Pedidos",
            descripcion = "Revisa el historial de tus pedidos",
            icono = Icons.Default.ShoppingBag
        ),
        OpcionMenu(
            titulo = "Mis Favoritos",
            descripcion = "Tus productos favoritos guardados",
            icono = Icons.Default.Favorite
        ),
        OpcionMenu(
            titulo = "Direcciones",
            descripcion = "Gestiona tus direcciones de entrega",
            icono = Icons.Default.Home
        ),
        OpcionMenu(
            titulo = "Método de Pago",
            descripcion = "Tarjetas y formas de pago",
            icono = Icons.Default.CreditCard
        ),
        OpcionMenu(
            titulo = "Configuración",
            descripcion = "Ajustes de la aplicación",
            icono = Icons.Default.Settings
        ),
        OpcionMenu(
            titulo = "Ayuda y Soporte",
            descripcion = "Centro de ayuda y contacto",
            icono = Icons.Default.Help
        )
    )
}