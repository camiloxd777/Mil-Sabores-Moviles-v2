package com.example.milsaboresmovilesv2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun DetalleProductoScreen(nombre: String, descripcion: String, precio: String, imagen: Int, carritoVM: CarritoViewModel, onIrCarrito:()-> Unit){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = imagen),
            contentDescription = nombre,
            modifier = Modifier.size(220.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5C3A21))
        Text(descripcion, fontSize = 15.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp))
        Text(
            text = "$$precio",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE66800),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                carritoVM.add(nombre,descripcion,imagen,precio)
                onIrCarrito()
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC6CF))
        ) {
            Text("Agregar al carrito", color = Color.White, fontSize = 16.sp)
        }
    }
}