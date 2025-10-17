package com.example.milsaboresmovilesv2.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.milsaboresmovilesv2.ui.screens.RegistroScreen
import com.example.milsaboresmovilesv2.ui.screens.ResumenScreen
import com.example.milsaboresmovilesv2.viewmodel.UsuarioViewModel
import androidx.navigation.compose.composable


@Composable
fun AppNavigation(){
    val navController= rememberNavController()

    val usuarioViewModel: UsuarioViewModel= viewModel()


    /*NavHost(
        navController=navController,
        startDestination="registro"
    ){
        composable(route= "registro"){
            RegistroScreen(navController,usuarioViewModel)
        }
        composable(route="resumen"){
            ResumenScreen(navController, usuarioViewModel)
        }
    }*/
}