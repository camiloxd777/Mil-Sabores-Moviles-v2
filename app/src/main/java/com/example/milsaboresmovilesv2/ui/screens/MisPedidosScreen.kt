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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel

@Composable
fun MisPedidosScreen(navController: NavController, userVM: UserViewModel) {
    val loggedUser = userVM.loginState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
    ) {
        if (loggedUser == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Inicia sesión para ver tus pedidos",
                        fontSize = 18.sp,
                        color = Color(0xFF666666)
                    )
                    Button(
                        onClick = { navController.navigate("login") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD35400)
                        )
                    ) {
                        Text("Iniciar Sesión")
                    }
                }
            }
        } else {
            Text(
                text = "Mis Pedidos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            val pedidos = listOf(
                Pedido("P-001", "15 Oct 2025", "$45.000", "Entregado"),
                Pedido("P-002", "20 Oct 2025", "$38.000", "Entregado"),
                Pedido("P-003", "25 Oct 2025", "$52.000", "En proceso")
            )

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidos) { pedido ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Pedido #${pedido.numero}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    pedido.fecha,
                                    color = Color(0xFF666666)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    pedido.total,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD35400)
                                )
                                Text(
                                    pedido.estado,
                                    color = when(pedido.estado){
                                        "Entregado" -> Color(0xFF27AE60)
                                        "En proceso" -> Color(0xFFF39C12)
                                        else -> Color(0xFF666666)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Pedido(
    val numero: String,
    val fecha: String,
    val total: String,
    val estado: String
)