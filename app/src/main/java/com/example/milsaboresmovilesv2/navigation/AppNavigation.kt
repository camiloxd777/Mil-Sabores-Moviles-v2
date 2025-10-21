package com.example.milsaboresmovilesv2.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.milsaboresmovilesv2.ui.screens.HomeScreenCompacta
import com.example.milsaboresmovilesv2.ui.screens.ProductosScreen
import com.example.milsaboresmovilesv2.ui.screens.MenuScreen
import androidx.compose.material3.Scaffold
import com.example.milsaboresmovilesv2.ui.components.TopBar
import androidx.compose.ui.graphics.Color
import com.example.milsaboresmovilesv2.ui.screens.ScreenPrincipal

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable("home") { ScreenPrincipal() } //se duplica porque no hay nada nuevo
            composable("productos") { ProductosScreen() } //pestaña productos
            composable("menu") { MenuScreen() }//se duplica porque no hay nada nuevo
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavItem("home", Icons.Default.Home, "Home"),
        NavItem("productos", Icons.Default.ShoppingBag, "Productos"),
        NavItem("menu", Icons.Default.Menu, "Menú")
    )

    NavigationBar(containerColor = Color(0xFFFFEAC4)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { androidx.compose.material3.Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home")
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

data class NavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)
