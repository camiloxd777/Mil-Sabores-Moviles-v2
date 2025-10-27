package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onGoToLogin: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var codigoPromo by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var aceptarTerminos by remember { mutableStateOf(false) }

    // scroll
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .padding(24.dp)
            .verticalScroll(scrollState), // 🔹 Aquí se activa el scroll
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Título principal
        Text(
            text = "Crear Cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )

        Text(
            text = "¡Regístrate para disfrutar de los sabores más dulces de todo Chile!",
            fontSize = 16.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // formulario
        RegisterTextField("Correo Electrónico", email, { email = it }, Icons.Default.Email)
        RegisterTextField("Nombre Completo", nombre, { nombre = it }, Icons.Default.Person)
        RegisterTextField("Nombre de Usuario", usuario, { usuario = it }, Icons.Default.Person)
        RegisterTextField("Fecha de Nacimiento (DD/MM/AAAA)", fechaNacimiento, { fechaNacimiento = it }, Icons.Default.Cake)

        // contraseñas
        RegisterPasswordField("Contraseña", password, { password = it }, passwordVisible, { passwordVisible = !passwordVisible })
        RegisterPasswordField("Confirmar Contraseña", confirmarPassword, { confirmarPassword = it }, passwordVisible, { passwordVisible = !passwordVisible })

        Spacer(modifier = Modifier.height(10.dp))

        // código promocional
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Tienes un código promocional?",
                fontSize = 14.sp,
                color = Color(0xFF8B4513),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = codigoPromo,
                onValueChange = { codigoPromo = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("Escríbelo aquí (opcional)") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFFE67E22),
                    unfocusedIndicatorColor = Color(0xFFCCCCCC),
                    cursorColor = Color(0xFFE67E22)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox de términos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = aceptarTerminos,
                onCheckedChange = { aceptarTerminos = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFE67E22))
            )
            Text(
                text = "Acepto los Términos y Condiciones",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón de registro
        Button(
            onClick = {
                if (password == confirmarPassword && aceptarTerminos) {
                    onRegisterSuccess()
                }
            },
            enabled = aceptarTerminos && email.isNotEmpty() && nombre.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD35400),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Iniciar sesión
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿Ya tienes una cuenta?", fontSize = 14.sp, color = Color(0xFF666666))
            TextButton(onClick = onGoToLogin) {
                Text(
                    text = "Inicia Sesión",
                    color = Color(0xFFE67E22),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun RegisterTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Color(0xFF888888)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE67E22),
            unfocusedIndicatorColor = Color(0xFFCCCCCC),
            cursorColor = Color(0xFFE67E22)
        )
    )
}

@Composable
fun RegisterPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggleVisibility: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF888888)) },
        trailingIcon = {
            val icon = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility
            IconButton(onClick = onToggleVisibility) {
                Icon(icon, contentDescription = null, tint = Color(0xFF888888))
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE67E22),
            unfocusedIndicatorColor = Color(0xFFCCCCCC),
            cursorColor = Color(0xFFE67E22)
        )
    )
}