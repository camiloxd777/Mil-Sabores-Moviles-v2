package com.example.milsaboresmovilesv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    private val _loginState = MutableStateFlow<User?>(null)
    val loginState: StateFlow<User?> get() = _loginState

    private val _registerState = MutableStateFlow(false)
    val registerState: StateFlow<Boolean> get() = _registerState

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

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

            if (repository.emailExists(user.email)) {
                _error.value = "El correo ya está registrado"
                return@launch
            }

            if (repository.usernameExists(user.username)) {
                _error.value = "El nombre de usuario ya existe"
                return@launch
            }

            repository.registerUser(user)
            _registerState.value = true
            loadUsers()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(email, password)

            if (user != null) {
                _loginState.value = user
            } else {
                _error.value = "Correo o contraseña incorrectos"
            }
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            repository.deleteUser(user)
            loadUsers()
        }
    }
}

