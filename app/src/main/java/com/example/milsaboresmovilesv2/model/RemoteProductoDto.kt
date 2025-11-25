package com.example.milsaboresmovilesv2.model

data class RemoteProductDto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val categoria: String,
    val activo: Boolean
)

data class RemoteProductRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val categoria: String,
    val activo: Boolean = true
)