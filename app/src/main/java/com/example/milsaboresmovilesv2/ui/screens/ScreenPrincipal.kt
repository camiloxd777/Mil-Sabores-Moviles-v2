package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.ui.components.BottomNavBar
import com.example.milsaboresmovilesv2.ui.components.TopBar
import com.example.milsaboresmovilesv2.ui.utils.obtenerWindowWidthSizeClass
import androidx.compose.runtime.*

@Composable
fun ScreenPrincipal(){
    var selectedItem by remember { mutableStateOf("Home") }

    Scaffold (
        topBar = {TopBar()},
        bottomBar = { BottomNavBar(selectedItem) {selectedItem = it} }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF5E1))
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            when (selectedItem){ //renderiza un text segun el item seleccionado
                "Home" -> Text("Bienvenido a Pastelería Mil Sabores", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                "Productos" -> Text("Productos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                "Menu" -> Text("Opciones del menú", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}














/*@Composable
fun HomeScreen(){
    val widthSizeClass= obtenerWindowWidthSizeClass()
    when (widthSizeClass){
        WindowWidthSizeClass.Compact->HomeScreenCompacta()
        WindowWidthSizeClass.Compact->HomeScreenMedium()
        WindowWidthSizeClass.Compact->HomeScreenGrande()
    }
}

@Preview(name="Compact", widthDp = 360, heightDp = 800)
@Composable
fun PreviewCompact(){
    HomeScreenCompacta()
}*/