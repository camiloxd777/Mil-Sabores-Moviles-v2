package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresmovilesv2.utils.openEmail
import com.example.milsaboresmovilesv2.utils.openPhoneDialer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyudaScreen(navController: NavController) {
    val context = LocalContext.current
    val preguntasFrecuentes = listOf(
        FAQ("¿Cómo realizo un pedido?", "Puedes realizar pedidos desde la sección de productos."),
        FAQ("¿Cuáles son los horarios de entrega?", "Entregamos de lunes a viernes de 9:00 a 20:00."),
        FAQ("¿Aceptan tarjetas de crédito?", "Sí, aceptamos todas las tarjetas principales."),
        FAQ("¿Hacen envíos a domicilio?", "Sí, realizamos envíos a toda la ciudad.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "¿Necesitas ayuda?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Contáctanos por cualquiera de estos medios:")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))

            ) {
                Column {
                    ContactoItem(
                        icon = Icons.Default.Phone,
                        text = "Llámanos: +56 9 12345678",
                        onClick = {
                            openPhoneDialer(context, "+56912345678")
                        }
                    )
                    ContactoItem(
                        icon = Icons.Default.Email,
                        text = "Escríbenos: soporte@milsabores.cl",
                        onClick = {
                            openEmail(context, "soporte@milsabores.cl")
                        }
                    )
                }
            }
        }

        item {
            Text(
                "Preguntas Frecuentes",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        items(preguntasFrecuentes) { faq ->
            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(2.dp) ,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))

            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        faq.pregunta,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        faq.respuesta,
                        color = Color(0xFF666666)
                    )
                }
            }
        }
    }
}

data class FAQ(
    val pregunta: String,
    val respuesta: String
)