package com.example.milsaboresmovilesv2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.milsaboresmovilesv2.model.UsuarioErrores
import com.example.milsaboresmovilesv2.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    //estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())

    //estado expuesto para la ui
    val estado: StateFlow<UsuarioUiState> = _estado

    //actualiza el campo nombre y limpia su error
    fun onNombreChange(valor:String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    //actualiza el campo correo
    fun onCorreoChange(valor:String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    //actualiza el campo clave
    fun onClaveChange(valor: String){
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    //actualiza el campo dirección
    fun onDireccionChange(valor: String){
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    //actualiza checkbox de aceptacion
    fun onAceptarTerminosChange(valor: Boolean){
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    //validacion global del formulario
    fun validarFormulario(): Boolean{
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains(other = "0")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null

        )
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return hayErrores
    }
}