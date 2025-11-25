package com.example.milsaboresmovilesv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.remote.RemoteUserDto
import com.example.milsaboresmovilesv2.data.remote.UpdateUserRequest
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository): ViewModel() {

    private val _loginState = MutableStateFlow<User?>(null)
    val loginState: StateFlow<User?> get() = _loginState

    // respuesta de la API (token + UserDto con rol)
    private val _remoteLoginState = MutableStateFlow<LoginResponse?>(null)
    val remoteLoginState: StateFlow<LoginResponse?> get() = _remoteLoginState

    private val _registerState = MutableStateFlow(false)
    val registerState: StateFlow<Boolean> get() = _registerState

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _adminUsers = MutableStateFlow<List<RemoteUserDto>>(emptyList())
    val adminUsers: StateFlow<List<RemoteUserDto>> get() = _adminUsers

    private val _remoteUsers = MutableStateFlow<List<RemoteUserDto>>(emptyList())
    val remoteUsers: StateFlow<List<RemoteUserDto>> get() = _remoteUsers

    private val _isRemoteLoading = MutableStateFlow(false)
    val isRemoteLoading: StateFlow<Boolean> get() = _isRemoteLoading

    private val _remoteToken = MutableStateFlow<String?>(null)
    val remoteToken: StateFlow<String?> get() = _remoteToken

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = repository.getAllUsers()
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            _error.value = null
            _registerState.value = false

            // Validación local
            if (repository.emailExists(user.email)) {
                _error.value = "El correo ya está registrado"
                return@launch
            }

            if (repository.usernameExists(user.username)) {
                _error.value = "El nombre de usuario ya existe"
                return@launch
            }

            try {
                // Registrar en la API Spring
                repository.registerRemote(
                    email = user.email,
                    nombre = user.nombre,
                    username = user.username,
                    fechaNacimiento = user.fechaNacimiento,
                    password = user.password,
                    codigoPromo = user.codigoPromo
                )
            } catch (e: Exception) {
                // Si falla el servidor, mostramos error y NO seguimos
                _error.value = "Error al registrar en el servidor"
                return@launch
            }

            repository.registerUser(user)

            _registerState.value = true
            loadUsers()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _error.value = null
            _remoteLoginState.value = null

            try {
                // 1) LOGIN REMOTO (Spring)
                val response = repository.loginRemote(email, password)
                _remoteLoginState.value = response

                val remoteUser = User(
                    id = 0,
                    email = response.user.email,
                    nombre = response.user.nombre,
                    username = response.user.username,
                    fechaNacimiento = response.user.fechaNacimiento,
                    password = "",
                    codigoPromo = response.user.codigoPromo
                )
                _loginState.value = remoteUser
                return@launch

            } catch (e: Exception) {
                // mostramos el error REAL
                e.printStackTrace()
                _error.value = "Error remoto: ${e.message}"
                return@launch
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
            loadUsers()
        }
    }

    fun logout() {
        _loginState.value = null
        _remoteLoginState.value = null
    }
    fun loginRemote(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isRemoteLoading.value = true
                val response = repository.loginRemote(email, password)
                _remoteToken.value = response.token
                // aquí decides si navegas a admin o user según response.user.rol
            } catch (e: Exception) {
                _error.value = "Error remoto: ${e.message}"
            } finally {
                _isRemoteLoading.value = false
            }
        }
    }

    // Cargar usuarios del backend (para GestionUsuariosScreen)
    fun loadRemoteUsers() {
        viewModelScope.launch {
            try {
                _isRemoteLoading.value = true
                val token = _remoteToken.value
                _remoteUsers.value = repository.getRemoteUsers(token)
            } catch (e: Exception) {
                _error.value = "Error al cargar usuarios remotos: ${e.message}"
            } finally {
                _isRemoteLoading.value = false
            }
        }
    }

    // Actualizar un usuario
    fun updateRemoteUser(
        id: Long,
        nombre: String? = null,
        username: String? = null,
        email: String? = null,
        password: String? = null,
        rol: String? = null
    ) {
        viewModelScope.launch {
            try {
                val request = UpdateUserRequest(
                    nombre = nombre,
                    username = username,
                    email = email,
                    password = password,
                    rol = rol
                )
                val updated = repository.updateRemoteUser(id, request)

                _remoteUsers.value = _remoteUsers.value.map {
                    if (it.id == id) updated else it
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar usuario: ${e.message}"
            }
        }
    }

    // Eliminar un usuario
    fun deleteRemoteUser(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteRemoteUser(id)
                _remoteUsers.value = _remoteUsers.value.filterNot { it.id == id }
            } catch (e: Exception) {
                _error.value = "Error al eliminar usuario: ${e.message}"
            }
        }
    }
}