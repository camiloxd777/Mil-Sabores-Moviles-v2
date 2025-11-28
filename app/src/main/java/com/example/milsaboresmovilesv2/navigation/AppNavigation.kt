package com.example.milsaboresmovilesv2.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.milsaboresmovilesv2.data.local.AppDatabase
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.ui.components.DetalleProductoScreen
import com.example.milsaboresmovilesv2.ui.components.TopBar
import com.example.milsaboresmovilesv2.ui.screens.*
import com.example.milsaboresmovilesv2.ui.screens.admin.*
import com.example.milsaboresmovilesv2.ui.utils.getProductImage
import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import com.example.milsaboresmovilesv2.viewmodel.ProductViewModel
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import com.example.milsaboresmovilesv2.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val carritoVM: CarritoViewModel = viewModel()

    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mil_sabores_db"
    ).build()

    val repository = UserRepository(db.userDao())
    val userVM: UserViewModel = viewModel(factory = UserViewModelFactory(repository))

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val adminRoutes = setOf(
        "adminProfile",
        "adminGestionProductos",
        "adminGestionPedidos",
        "adminEstadisticas",
        "adminGestionUsuarios",
        "adminEditProfile"
    )

    val userScreensWithNavBar = setOf("home", "productos", "menu")

    val showCart = currentRoute !in adminRoutes

    Scaffold(
        topBar = { TopBar(navController, badgeCount = carritoVM.totalItems()) },
        bottomBar = {
            if (currentRoute in userScreensWithNavBar) {
                BottomNavBar(navController)
            } else if (currentRoute in adminRoutes) {
                AdminBottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { ScreenPrincipal(carritoVM = carritoVM, navController = navController, userVM = userVM) }
            composable("productos") { 
                val productVM: ProductViewModel = viewModel()
                ProductosScreen(navController, carritoVM, productVM) 
            }
            composable("menu") { MenuScreen(navController, userVM) }
            composable(
                "detalleProducto/{nombre}/{descripcion}/{precio}/{imagen}",
                arguments = listOf(
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("descripcion") { type = NavType.StringType },
                    navArgument("precio") { type = NavType.StringType },
                    navArgument("imagen") { type = NavType.IntType },
                )
            ) { backStackEntry ->
                val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
                val precio = backStackEntry.arguments?.getString("precio") ?: ""
                val imagen = backStackEntry.arguments?.getInt("imagen") ?: 0

                DetalleProductoScreen(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    imagen = imagen,
                    carritoVM = carritoVM,
                    onIrCarrito = { navController.navigate("carrito") },
                    navController = navController
                )
            }
            composable("carrito") { CarritoScreen(carritoVM) }
            composable("login") {
                LoginScreen(
                    userVM = userVM,
                    onUserLogInSuccess = { 
                        navController.navigate("home") { 
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true 
                        } 
                    },
                    onAdminLogInSuccess = { 
                        navController.navigate("adminProfile") { 
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true 
                        } 
                    },
                    onBackClick = { navController.popBackStack() },
                    onRegisterClick = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreen(
                    userVM = userVM,
                    onRegisterSuccess = { navController.navigate("login") },
                    onGoToLogin = { navController.popBackStack() }
                )
            }
            composable(route = "bdusers") {
                UsersScreen(userVM)
            }
            composable("misPedidos") { MisPedidosScreen(navController = navController, userVM = userVM) }
            composable("direcciones") { DireccionesScreen(navController, userVM) }
            composable("misFavoritos") { MisFavoritosScreen(navController, userVM) }
            composable("metodoPago") { MetodoPagoScreen(navController, userVM) }
            composable("configuracion") { ConfiguracionScreen(navController, userVM) }
            composable("ayuda") { AyudaScreen(navController = navController) }

            // Admin Routes
            composable("adminProfile") {
                AdminProfileScreen(
                    onEditProfile = { navController.navigate("adminEditProfile") },
                    onManageProducts = { navController.navigate("adminGestionProductos") },
                    onViewOrders = { navController.navigate("adminGestionPedidos") },
                    onViewStatistics = { navController.navigate("adminEstadisticas") },
                    onManageUsers = { navController.navigate("adminGestionUsuarios") },
                    onLogout = {
                        userVM.logout()
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("adminEditProfile") {
                AdminEditProfileScreen(
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = { navController.popBackStack() }
                )
            }
            composable("adminGestionProductos") {
                val productVM: ProductViewModel = viewModel()
                val adminProducts by productVM.adminProducts.collectAsState()
                val isLoading by productVM.isLoading.collectAsState()
                val error by productVM.error.collectAsState()
                val scope = rememberCoroutineScope()
                val token by userVM.remoteToken.collectAsState()

                LaunchedEffect(token) {
                    token?.let { productVM.loadAdminProducts(it) }
                }

                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    error.isNotEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(error, color = Color.Red)
                        }
                    }
                    else -> {
                        val uiProducts = adminProducts.map { remote ->
                            Product(
                                id = remote.id.toString(),
                                name = remote.nombre,
                                category = remote.categoria,
                                price = "$${remote.precio}",
                                inStock = remote.activo
                            )
                        }
                        GestionProductosScreen(
                            products = uiProducts,
                            onBackClick = { navController.popBackStack() },
                            onDeleteProduct = { idStr ->
                                token?.let {
                                    scope.launch {
                                        productVM.deleteProduct(it, idStr.toLong())
                                    }
                                }
                            },
                            onToggleStatus = { idStr, newState ->
                                token?.let {
                                    scope.launch {
                                        productVM.toggleProductActivo(it, idStr.toLong(), newState)
                                    }
                                }
                            }
                        )
                    }
                }
            }
            composable("adminGestionUsuarios") {
                val remoteUsers by userVM.remoteUsers.collectAsState()
                val token by userVM.remoteToken.collectAsState()

                LaunchedEffect(token) {
                    token?.let { userVM.loadRemoteUsers() }
                }

                GestionUsuariosScreen(
                    usuarios = remoteUsers.map { remote ->
                        Usuario(
                            id = remote.id.toString(),
                            nombre = remote.nombre,
                            email = remote.email,
                            rol = remote.rol,
                            activo = true, // Placeholder
                            fechaRegistro = "", // Placeholder
                            telefono = "", // Placeholder
                            ultimoAcceso = "" // Placeholder
                        )
                    },
                    onBackClick = { navController.popBackStack() },
                    onDeleteUser = { id -> 
                        token?.let { userVM.deleteRemoteUser(id.toLong()) }
                    }
                )
            }
            composable("adminEstadisticas") {
                EstadisticasScreen(onBackClick = { navController.popBackStack() })
            }
            composable("adminGestionPedidos") {
                GestionPedidosScreen(onBackClick = { navController.popBackStack() })
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
                label = { Text(item.label) },
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

@Composable
fun AdminBottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavItem("adminProfile", Icons.Default.Person, "Perfil"),
        NavItem("adminGestionProductos", Icons.Default.ShoppingBag, "Productos"),
        NavItem("adminGestionPedidos", Icons.Default.ShoppingCart, "Pedidos"),
        NavItem("adminGestionUsuarios", Icons.Default.People, "Usuarios"),
        NavItem("adminEstadisticas", Icons.Default.BarChart, "Stats")
    )

    NavigationBar(containerColor = Color(0xFFFFEAC4)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("adminProfile") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Gray,
                    unselectedIconColor = Color(0xFFD3C1B1),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFFD3C1B1),
                    indicatorColor = Color(0xFF6A360D)
                )
            )
        }
    }
}

data class NavItem(val route: String, val icon: ImageVector, val label: String)
