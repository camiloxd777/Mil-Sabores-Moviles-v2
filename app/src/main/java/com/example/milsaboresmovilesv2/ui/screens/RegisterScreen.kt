package com.example.milsaboresmovilesv2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel

@Composable
fun RegisterScreen(
    userVM: UserViewModel,
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

    // Control para animaciones de entrada
    var contentLoaded by remember { mutableStateOf(false) }

    // scroll
    val scrollState = rememberScrollState()

    //registrarse
    val registerSuccess by userVM.registerState.collectAsState()
    val errorMsg by userVM.error.collectAsState()

    LaunchedEffect(Unit) {
        contentLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF2))
            .padding(22.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Título principal
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(800)) +
                    slideInVertically(initialOffsetY = { -it }, animationSpec = tween(800))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Crear Cuenta",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Regístrate para disfrutar de los sabores más dulces de todo Chile!",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )
                errorMsg?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // formulario
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 200))
        ) {
            RegisterTextField("Correo Electrónico", email, { email = it }, Icons.Default.Email)
        }

        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 300))
        ) {
            RegisterTextField("Nombre Completo", nombre, { nombre = it }, Icons.Default.Person)
        }

        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 400))
        ) {
            RegisterTextField("Nombre de Usuario", usuario, { usuario = it }, Icons.Default.Person)
        }

        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 500))
        ) {
            RegisterTextField("Fecha de Nacimiento (DD/MM/AAAA)", fechaNacimiento, { fechaNacimiento = it }, Icons.Default.Cake)
        }

        // contraseñas
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 600))
        ) {
            RegisterPasswordField("Contraseña", password, { password = it }, passwordVisible, { passwordVisible = !passwordVisible })
        }

        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 700))
        ) {
            RegisterPasswordField("Confirmar Contraseña", confirmarPassword, { confirmarPassword = it }, passwordVisible, { passwordVisible = !passwordVisible })
        }

        Spacer(modifier = Modifier.height(8.dp))

        // código promocional
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 800))
        ) {
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
                AnimatedOutlinedTextField(
                    value = codigoPromo,
                    onValueChange = { codigoPromo = it },
                    placeholder = "Escríbelo aquí (opcional)"
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Checkbox de términos
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 900))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedCheckbox(
                    checked = aceptarTerminos,
                    onCheckedChange = { aceptarTerminos = it }
                )
                Text(
                    text = "Acepto los Términos y Condiciones",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Botón
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 1000))
        ) {
            AnimatedRegisterButton(
                enabled = aceptarTerminos && email.isNotEmpty() && nombre.isNotEmpty() && password.isNotEmpty(),
                onClick = {
                    if (password == confirmarPassword && aceptarTerminos) {
                        val user = User(
                            email = email,
                            username = usuario,
                            password = password,
                            nombre = nombre,
                            fechaNacimiento = fechaNacimiento,
                            codigoPromo = codigoPromo.ifEmpty { null }
                        )
                        userVM.register(user)
                        /*onRegisterSuccess()*/
                    }
                }
            )
            LaunchedEffect(registerSuccess){
                if (registerSuccess){
                    onRegisterSuccess()
                }
            }


        }

        Spacer(modifier = Modifier.height(8.dp))

        // Iniciar sesión
        AnimatedVisibility(
            visible = contentLoaded,
            enter = fadeIn(animationSpec = tween(600, delayMillis = 1100))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("¿Ya tienes una cuenta?", fontSize = 14.sp, color = Color(0xFF666666))
                AnimatedTextButton(onClick = onGoToLogin) {
                    Text(
                        text = "Inicia Sesión",
                        color = Color(0xFFE67E22),
                        fontWeight = FontWeight.Medium
                    )
                }
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
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF888888)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE67E22),
            unfocusedIndicatorColor = Color(0xFFCCCCCC),
            cursorColor = Color(0xFFE67E22)
        ),
        interactionSource = interactionSource
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
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = Color(0xFF888888)
            )
        },
        trailingIcon = {
            val icon = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility
            AnimatedIconButton(
                onClick = onToggleVisibility,
                icon = icon
            )
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE67E22),
            unfocusedIndicatorColor = Color(0xFFCCCCCC),
            cursorColor = Color(0xFFE67E22)
        ),
        interactionSource = interactionSource
    )
}

@Composable
fun AnimatedOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(placeholder) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE67E22),
            unfocusedIndicatorColor = Color(0xFFCCCCCC),
            cursorColor = Color(0xFFE67E22)
        ),
        interactionSource = interactionSource
    )
}

@Composable
fun AnimatedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (checked) 1.1f else 1f,
        animationSpec = tween(200)
    )

    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(checkedColor = Color(0xFFE67E22)),
        modifier = Modifier.scale(scale)
    )
}

@Composable
fun AnimatedRegisterButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100)
    )

    val buttonColor by animateColorAsState(
        targetValue = if (enabled) Color(0xFFD35400) else Color(0xFFCCCCCC),
        animationSpec = tween(300)
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        interactionSource = interactionSource
    ) {
        Text(
            text = "Registrarse",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AnimatedTextButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.6f else 1f,
        animationSpec = tween(100)
    )

    TextButton(
        onClick = onClick,
        modifier = Modifier.alpha(alpha),
        interactionSource = interactionSource
    ) {
        content()
    }
}

@Composable
fun AnimatedIconButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.8f else 1f,
        animationSpec = tween(100)
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier.scale(scale),
        interactionSource = interactionSource
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF888888))
    }
}

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onRegisterSuccess = { /* Acción al registrar exitosamente */ },
        onBackClick = { /* Acción al hacer clic en retroceso */ },
        onGoToLogin = { /* Acción para ir al login */ }
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun RegisterScreenPreviewMobile() {
    RegisterScreen(
        onRegisterSuccess = { },
        onBackClick = { },
        onGoToLogin = { }
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=673dp,height=841dp")
@Composable
fun RegisterScreenPreviewTablet() {
    RegisterScreen(
        onRegisterSuccess = { },
        onBackClick = { },
        onGoToLogin = { }
    )
}*/