package com.example.milsaboresmovilesv2

import com.example.milsaboresmovilesv2.data.local.User
import com.example.milsaboresmovilesv2.data.repository.UserRepository
import com.example.milsaboresmovilesv2.model.LoginResponse
import com.example.milsaboresmovilesv2.model.UserDto
import com.example.milsaboresmovilesv2.viewmodel.UserViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest : StringSpec({

    lateinit var mockRepo: UserRepository
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        mockRepo = mockk(relaxed = true) // relaxed para no mockear todas las llamadas
    }

    afterTest {
        Dispatchers.resetMain()
    }

    "login exitoso debe actualizar remoteLoginState" {
        runTest {
            val email = "test@test.com"
            val password = "1234"
            val mockResponse = LoginResponse(
                token = "fake-token",
                user = UserDto(1L, email, "Test User", "testuser", "2000-01-01", null, "USER")
            )

            coEvery { mockRepo.loginRemote(email, password) } returns mockResponse

            val viewModel = UserViewModel(mockRepo)

            viewModel.login(email, password)
            advanceUntilIdle()

            viewModel.remoteLoginState.value shouldBe mockResponse
            viewModel.loginState.value?.email shouldBe email
        }
    }

    "login fallido debe setear error" {
        runTest {
            val email = "test@test.com"
            val password = "wrongpass"
            val errorMessage = "Credenciales inválidas"

            coEvery { mockRepo.loginRemote(email, password) } throws RuntimeException(errorMessage)

            val viewModel = UserViewModel(mockRepo)

            viewModel.login(email, password)
            advanceUntilIdle()

            viewModel.error.value shouldBe "Error remoto: $errorMessage"
        }
    }

    "registro exitoso debe poner registerState en true" {
        runTest {
            val user = User(0, "new@test.com", "New User", "newuser", "2000-01-01", "1234", null)

            coEvery { mockRepo.emailExists(user.email) } returns false
            coEvery { mockRepo.usernameExists(user.username) } returns false
            coEvery { mockRepo.registerRemote(any(), any(), any(), any(), any(), any()) } returns UserDto(1L, user.email, user.nombre, user.username, user.fechaNacimiento, user.codigoPromo, "USER")
            coEvery { mockRepo.registerUser(user) } returns Unit

            val viewModel = UserViewModel(mockRepo)

            viewModel.register(user)
            advanceUntilIdle()

            viewModel.registerState.value shouldBe true
            coVerify { mockRepo.registerUser(user) } // Verificamos que se llamó al repo local
        }
    }

    "registro con email existente debe setear error" {
        runTest {
            val user = User(0, "exist@test.com", "Exist User", "existuser", "2000-01-01", "1234", null)

            coEvery { mockRepo.emailExists(user.email) } returns true

            val viewModel = UserViewModel(mockRepo)

            viewModel.register(user)
            advanceUntilIdle()

            viewModel.error.value shouldBe "El correo ya está registrado"
            viewModel.registerState.value shouldBe false
        }
    }

    "registro con username existente debe setear error" {
        runTest {
            val user = User(0, "another@test.com", "Another User", "existuser", "2000-01-01", "1234", null)

            coEvery { mockRepo.emailExists(user.email) } returns false
            coEvery { mockRepo.usernameExists(user.username) } returns true

            val viewModel = UserViewModel(mockRepo)

            viewModel.register(user)
            advanceUntilIdle()

            viewModel.error.value shouldBe "El nombre de usuario ya existe"
            viewModel.registerState.value shouldBe false
        }
    }

    "registro con fallo en API debe setear error y no registrar localmente" {
        runTest {
            val user = User(0, "api@fail.com", "API Fail", "apifail", "2000-01-01", "1234", null)

            coEvery { mockRepo.emailExists(user.email) } returns false
            coEvery { mockRepo.usernameExists(user.username) } returns false
            coEvery { mockRepo.registerRemote(any(), any(), any(), any(), any(), any()) } throws RuntimeException("Error de red")

            val viewModel = UserViewModel(mockRepo)

            viewModel.register(user)
            advanceUntilIdle()

            viewModel.error.value shouldBe "Error al registrar en el servidor"
            viewModel.registerState.value shouldBe false
            coVerify(exactly = 0) { mockRepo.registerUser(user) }
        }
    }
})