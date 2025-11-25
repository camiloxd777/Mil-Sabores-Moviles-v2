package com.example.milsaboresmovilesv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresmovilesv2.data.repository.ProductRepository
import com.example.milsaboresmovilesv2.model.RemoteProductDto
import com.example.milsaboresmovilesv2.model.RemoteProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _products = MutableStateFlow<List<RemoteProductDto>>(emptyList())
    val products: StateFlow<List<RemoteProductDto>> = _products

    private val _adminProducts = MutableStateFlow<List<RemoteProductDto>>(emptyList())
    val adminProducts: StateFlow<List<RemoteProductDto>> = _adminProducts

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _products.value = repository.getRemoteProducts()
            } catch (e: Exception) {
                _error.value = "Error cargando productos: ${e.message}"
            }
        }
    }

    fun loadAdminProducts() {
        viewModelScope.launch {
            try {
                _adminProducts.value = repository.getRemoteProductsAdmin()
            } catch (e: Exception) {
                _error.value = "Error cargando productos admin: ${e.message}"
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteRemoteProduct(id)
                _adminProducts.value = _adminProducts.value.filterNot { it.id == id }
            } catch (e: Exception) {
                _error.value = "Error eliminando producto: ${e.message}"
            }
        }
    }

    fun toggleProductActivo(id: Long, nuevoActivo: Boolean) {
        viewModelScope.launch {
            try {
                val product = _adminProducts.value.firstOrNull { it.id == id } ?: return@launch
                val req = RemoteProductRequest(
                    nombre = product.nombre,
                    descripcion = product.descripcion,
                    precio = product.precio,
                    categoria = product.categoria,
                    activo = nuevoActivo
                )
                val updated = repository.updateRemoteProduct(id, req)
                _adminProducts.value = _adminProducts.value.map {
                    if (it.id == id) updated else it
                }
            } catch (e: Exception) {
                _error.value = "Error cambiando estado: ${e.message}"
            }
        }
    }

    fun addProduct(req: RemoteProductRequest) {
        viewModelScope.launch {
            try {
                val created = repository.addRemoteProduct(req)
                _adminProducts.value = _adminProducts.value + created
            } catch (e: Exception) {
                _error.value = "Error agregando producto: ${e.message}"
            }
        }
    }
}