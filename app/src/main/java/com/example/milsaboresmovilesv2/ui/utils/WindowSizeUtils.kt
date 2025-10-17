package com.example.milsaboresmovilesv2.ui.utils
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowWidthSizeClass(): WindowWidthSizeClass{
    val isInPreview = LocalInspectionMode.current
    if (isInPreview){
        return WindowWidthSizeClass.Compact
    }
    return calculateWindowSizeClass(LocalActivity.current as android.app.Activity).widthSizeClass

}