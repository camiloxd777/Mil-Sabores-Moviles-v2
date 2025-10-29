package com.example.milsaboresmovilesv2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.milsaboresmovilesv2.ui.screens.Producto


data class CarritoItem(val nombre: String, val descripcion: String, val imagen: Int, val precioUnitario: Int, var cantidad: Int = 1)


class CarritoViewModel: ViewModel(){

    private val _items = mutableStateListOf<CarritoItem>()
    val items: List<CarritoItem> get()= _items

    fun add(
        nombre: String,
        descripcion: String,
        imagen: Int,
        precioTexto: String
    ){
        val precio = parsePrecio(precioTexto)
        val idx = _items.indexOfFirst { it.nombre == nombre }
        if(idx >=0){
            _items[idx]=_items[idx].copy(cantidad = _items[idx].cantidad + 1)
        }else{
            _items.add(
                CarritoItem(
                    nombre = nombre,
                    descripcion = descripcion,
                    imagen = imagen,
                    precioUnitario = precio,
                    cantidad = 1
                )
            )
        }
    }

    fun inc(nombre: String){
        val i = _items.indexOfFirst { it.nombre == nombre }
        if (i >= 0) _items[i] = _items[i].copy(cantidad = _items[i].cantidad + 1)
    }

    fun dec(nombre: String){
        val i = _items.indexOfFirst { it.nombre == nombre }
        if (i>=0){
            val c = _items[i]
            if(c.cantidad>1){
                _items[i] = c.copy(cantidad = c.cantidad-1)
            }else{
                _items.removeAt(i)
            }
        }
    }

    fun remove(nombre: String){
        _items.removeAll{it.nombre == nombre}
    }

    fun clear() = _items.clear()
    fun total(): Int = _items.sumOf { it.precioUnitario * it.cantidad }
    fun totalItems(): Int = _items.sumOf { it.cantidad }

    private fun parsePrecio(texto: String): Int {
        val digits = texto.filter { it.isDigit() }
        return digits.toIntOrNull() ?: 0
    }


}