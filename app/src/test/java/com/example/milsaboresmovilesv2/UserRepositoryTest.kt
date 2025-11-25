package com.example.milsaboresmovilesv2

import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.local.UserDao
import com.example.milsaboresmovilesv2.data.remote.RemoteUserDto
import com.example.milsaboresmovilesv2.data.remote.UpdateUserRequest
import com.example.milsaboresmovilesv2.data.remote.UserApiService
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.model.LoginRequest
import com.example.milsaboresmovilesv2.model.LoginResponse
import com.example.milsaboresmovilesv2.model.RegisterRequest
import com.example.milsaboresmovilesv2.model.UserDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test



class UserRepositoryTest {

    private val userDao: UserDao = mockk()
    private val api: UserApiService = mockk()

    private val repository = UserRepository(userDao, api)

    // LOCAL (ROOM)


    @Test
    fun `registerUser should insert user into local database`() = runTest {
        val user = User(
            id = 1,
            email = "test@mail.com",
            username = "test",
            password = "1234",
            nombre = "test",
            fechaNacimiento = "01/01/2013",
            codigoPromo = null
        )

        coEvery { userDao.insertUser(user) } returns Unit

        repository.registerUser(user)

        coVerify(exactly = 1) { userDao.insertUser(user) }
    }

    @Test
    fun `emailExists returns true if email is found`() = runTest {
        val user = User(1, "a@mail.com", "a", "a","01/01/2001","1234", null)

        coEvery { userDao.getUserByEmail("a@mail.com") } returns user

        val result = repository.emailExists("a@mail.com")

        assertTrue(result)
    }

    @Test
    fun `usernameExists returns true if username exists`() = runTest {
        val user = User(1, "b@mail.com", "user123", "b", "01/01/2001","1234", null)

        coEvery { userDao.getUserByUsername("user123") } returns user

        val result = repository.usernameExists("user123")

        assertTrue(result)
    }

    @Test
    fun `login returns user if credentials are correct`() = runTest {
        val user = User(1, "c@mail.com", "user", "c","01/01/2001","pass", null)

        coEvery { userDao.login("c@mail.com", "pass") } returns user

        val result = repository.login("c@mail.com", "pass")

        assertEquals(user, result)
    }

    @Test
    fun `getAllUsers returns list of users`() = runTest {
        val users = listOf(
            User(1, "a@mail.com", "a", "c", "01/01/2001","1", null),
            User(2, "b@mail.com", "b", "d", "01/01/2001","2", null)
        )

        coEvery { userDao.getAllUsers() } returns users

        val result = repository.getAllUsers()

        assertEquals(users, result)
    }

    @Test
    fun `deleteUser removes user from database`() = runTest {
        val user = User(1, "a@mail.com", "a","c", "01/01/2001", "1234", null)

        coEvery { userDao.deleteUser(user) } returns Unit

        repository.deleteUser(user)

        coVerify(exactly = 1) { userDao.deleteUser(user) }
    }

    // =====================================================
    // API REMOTA (SPRING)
    // =====================================================

    @Test
    fun `loginRemote returns login response`() = runTest {
        // Arrange: construir un UserDto válido
        val userDto = UserDto(
            id = 1,
            email = "test@mail.com",
            nombre = "Test User",
            username = "testuser",
            fechaNacimiento = "2000-01-01",
            codigoPromo = null,
            rol = "USER"
        )

        val response = LoginResponse(
            token = "abc123",
            user = userDto
        )

        coEvery { api.login(any<LoginRequest>()) } returns response

        // Act
        val result = repository.loginRemote("test@mail.com", "pass")

        // Assert
        assertEquals(response, result)
    }

    @Test
    fun `registerRemote returns UserDto`() = runTest {
        val dto = UserDto(
            id = 33,
            email = "new@mail.com",
            nombre = "Nuevo User",
            username = "nuevo",
            fechaNacimiento = "2000-01-01",
            codigoPromo = null,
            rol = "USER"
        )

        coEvery { api.register(any<RegisterRequest>()) } returns dto

        val result = repository.registerRemote(
            email = "new@mail.com",
            nombre = "Nuevo User",
            username = "nuevo",
            fechaNacimiento = "2000-01-01",
            password = "1234",
            codigoPromo = null
        )

        assertEquals(dto, result)
    }

    @Test
    fun `getRemoteUsers returns list of RemoteUserDto`() = runTest {
        val list = listOf(
            RemoteUserDto(
                id = 1,
                nombre = "Admin",
                email = "admin@mail.com",
                username = "admin123",
                rol = "ADMIN"
            )
        )

        coEvery { api.getUsers(any()) } returns list

        val result = repository.getRemoteUsers("token123")

        assertEquals(list, result)
    }

    @Test
    fun `updateRemoteUser returns updated user`() = runTest {
        val request = UpdateUserRequest(nombre = "Nuevo", username = "nuevo123")
        val updated = RemoteUserDto(
            id = 1,
            nombre = "Nuevo",
            email = "mail@mail.com",
            username = "nuevo123",
            rol = "USER"
        )

        coEvery { api.updateUser(1, request) } returns updated

        val result = repository.updateRemoteUser(1, request)

        assertEquals(updated, result)
    }

    @Test
    fun `deleteRemoteUser calls API delete`() = runTest {
        coEvery { api.deleteUser(5) } returns Unit

        repository.deleteRemoteUser(5)

        coVerify { api.deleteUser(5) }
    }
}
