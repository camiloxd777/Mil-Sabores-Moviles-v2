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
import com.example.milsaboresmovilesv2.ui.screens.ProductosScreen
import com.example.milsaboresmovilesv2.ui.screens.MenuScreen
import androidx.compose.material3.Scaffold
import com.example.milsaboresmovilesv2.ui.components.TopBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.milsaboresmovilesv2.data.local.AppDatabase
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.ui.components.DetalleProductoScreen
import com.example.milsaboresmovilesv2.ui.screens.BdUsersScreen
import com.example.milsaboresmovilesv2.ui.screens.CarritoScreen
import com.example.milsaboresmovilesv2.ui.screens.LoginScreen
import com.example.milsaboresmovilesv2.ui.screens.RegisterScreen
import com.example.milsaboresmovilesv2.ui.screens.ScreenPrincipal
import com.example.milsaboresmovilesv2.ui.screens.UsersScreen
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import com.example.milsaboresmovilesv2.viewmodel.UserViewModelFactory
import com.example.milsaboresmovilesv2.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val carritoVM: CarritoViewModel = viewModel()

    //creacion base de datos
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mil_sabores_db"
    ).build()

    val repository = UserRepository(db.userDao())
    val userVM: UserViewModel = viewModel(factory = UserViewModelFactory(repository))


    Scaffold(
        topBar = { TopBar(navController, badgeCount = carritoVM.totalItems()) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable("home") { ScreenPrincipal(carritoVM=carritoVM, navController=navController, userVM=userVM) }
            composable("productos") { ProductosScreen(navController, carritoVM) } //pestaña productos
            composable("menu") { MenuScreen(navController, userVM) }
            composable (
                "detalleProducto/{nombre}/{descripcion}/{precio}/{imagen}",
                arguments = listOf(
                    navArgument("nombre"){type = NavType.StringType},
                    navArgument("descripcion"){type = NavType.StringType},
                    navArgument("precio"){type = NavType.StringType},
                    navArgument("imagen"){type = NavType.IntType},
                )
            ){ backStackEntry ->
                val nombre = backStackEntry.arguments?.getString("nombre")?:""
                val descripcion = backStackEntry.arguments?.getString("descripcion")?:""
                val precio = backStackEntry.arguments?.getString("precio")?:""
                val imagen = backStackEntry.arguments?.getInt("imagen")?:0

                DetalleProductoScreen(nombre = nombre, descripcion = descripcion, precio = precio, imagen = imagen, carritoVM = carritoVM, onIrCarrito = {navController.navigate("carrito")})

            }
            composable("carrito") { CarritoScreen(carritoVM) }
            composable("login") {
                LoginScreen(
                    userVM = userVM,
                    onLogInSuccess = {navController.navigate("home")}, //vuelve al home después de iniciar sesión
                    onBackClick = {navController.popBackStack()}, //vuelve al menú
                    onRegisterClick = {navController.navigate("register")} //redirige al register
                )
            }
            composable("register") {
                RegisterScreen(
                    userVM = userVM,
                    onRegisterSuccess = {navController.navigate("login")},
                    onGoToLogin = {navController.popBackStack()}
                )
            }
            composable(route="bdusers") {
                UsersScreen(userVM)
            }
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
