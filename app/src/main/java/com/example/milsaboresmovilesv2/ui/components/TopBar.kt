package com.example.milsaboresmovilesv2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.R

@Composable
fun TopBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth() // ocupa todo el ancho
            .background(Color(0xFFFFF5E1)) // fondo color crema pastel
            .padding(12.dp), // espacio interno
        verticalAlignment = Alignment.CenterVertically // centra verticalmente los elementos
    ){
        Image(
            painter = painterResource(id = R.drawable.logo), //carga el logo
            contentDescription = "logo",
            modifier = Modifier.size(48.dp) //ajusta el tamaño
        )
        Spacer(modifier = Modifier.width(8.dp)) //separacion entre el logo y texto
        Text(
            text = "Pastelería Mil Sabores",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF5C3A21)
        )
    }
}