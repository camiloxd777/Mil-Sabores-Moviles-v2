package com.example.milsaboresmovilesv2.data.repository

import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.local.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) = userDao.insertUser(user)

    suspend fun emailExists(email: String): Boolean =
        userDao.getUserByEmail(email) != null

    suspend fun usernameExists(username: String): Boolean =
        userDao.getUserByUsername(username) != null

    suspend fun login(email: String, password: String): User? =
        userDao.login(email, password)

    suspend fun getAllUsers(): List<User> =
        userDao.getAllUsers()

    suspend fun deleteUser(user: User)=
        userDao.deleteUser(user)
}