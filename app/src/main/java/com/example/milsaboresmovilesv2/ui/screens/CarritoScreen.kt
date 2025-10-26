package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CarritoScreen(carritoVM: CarritoViewModel){
    val items = carritoVM.items
    val formato = NumberFormat.getInstance(Locale.forLanguageTag("es-CL"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ){
        Column(Modifier.fillMaxSize().padding(16.dp)){
            Text("Carrito de compras", fontSize = 23.sp, fontWeight = FontWeight.Bold,color=Color(0xFF5C3A21))
            Spacer(Modifier.height(12.dp))

            if (items.isEmpty()){
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text("Tu carrito está vacío...", color = Color.DarkGray)
                }
            }else{
                LazyColumn(Modifier.weight(1f)){
                    items(items.size){ i->
                        val it = items[i]
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp)) {
                                Image(
                                    painter = painterResource(id = it.imagen),
                                    contentDescription = it.nombre,
                                    modifier = Modifier.size(70.dp)
                                )
                                Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                                    Text(it.nombre, fontWeight = FontWeight.SemiBold)
                                    Text("Precio: $${formato.format(it.precioUnitario)}", color = Color(0xFFE66B00))
                                    Text("Subtotal: $${formato.format(it.precioUnitario * it.cantidad)}")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedButton(onClick = {carritoVM.dec(it.nombre)}) {Text("-") }
                                    Text("${it.cantidad}", modifier = Modifier.padding(horizontal = 8.dp))
                                    OutlinedButton(onClick = {carritoVM.inc(it.nombre)}) {Text("+") }
                                }
                            }
                        }
                    }
                }

                val total = carritoVM.total()
                Text(
                    "TOTAL: $${formato.format(total)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5C3A21),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Button(
                    onClick = {/*pal checkout*/},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC6CF)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {Text("Finalizar Compra", color = Color.White)}


            }

        }
    }



}