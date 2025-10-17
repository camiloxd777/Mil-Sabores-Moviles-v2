package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.milsaboresmovilesv2.ui.utils.obtenerWindowWidthSizeClass

@Composable
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
}