package com.example.milsaboresmovilesv2.data.repository

import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.local.UserDao
import com.example.milsaboresmovilesv2.model.LoginRequest
import com.example.milsaboresmovilesv2.model.LoginResponse
import com.example.milsaboresmovilesv2.model.RegisterRequest
import com.example.milsaboresmovilesv2.data.remote.RetrofitInstance
import com.example.milsaboresmovilesv2.data.remote.UserApiService

class UserRepository(
    private val userDao: UserDao,
    private val api: UserApiService = RetrofitInstance.userApi
) {

    // LOCAL (ROOM)

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun emailExists(email: String): Boolean =
        userDao.getUserByEmail(email) != null

    suspend fun usernameExists(username: String): Boolean =
        userDao.getUserByUsername(username) != null

    suspend fun login(email: String, password: String): User? =
        userDao.login(email, password)

    suspend fun getAllUsers(): List<User> =
        userDao.getAllUsers()

    suspend fun deleteUser(user: User) =
        userDao.deleteUser(user)



    // SPRING API
    suspend fun loginRemote(email: String, password: String): LoginResponse {
        val request = LoginRequest(email = email, password = password)
        return api.login(request) // Llama a UserApiService.login()
    }

    suspend fun registerRemote(
        email: String,
        nombre: String,
        username: String,
        fechaNacimiento: String,
        password: String,
        codigoPromo: String?
    ) = api.register(
        RegisterRequest(
            email = email,
            nombre = nombre,
            username = username,
            fechaNacimiento = fechaNacimiento,
            password = password,
            codigoPromo = codigoPromo
        )
    )

    suspend fun getRemoteUsers(token: String? = null) =
        api.getUsers(token?.let { "Bearer $it" })
}


