package com.example.milsaboresmovilesv2.ui.utils

import com.example.milsaboresmovilesv2.R

fun getProductImage(nombre: String, categoria: String): Int {
    val n = nombre.lowercase()
    val c = categoria.lowercase()

    // === Tortas Cuadradas ===
    if (c.contains("cuadrad")) {
        return when {
            n.contains("chocolate") -> R.drawable.torta_chocolate
            n.contains("fruta") -> R.drawable.torta_cuadrada
            else -> R.drawable.torta_cuadrada
        }
    }

    // === Tortas Circulares ===
    if (c.contains("circular")) {
        return when {
            n.contains("manjar") -> R.drawable.torta_manjar_v2
            n.contains("vainilla") -> R.drawable.torta_vainilla
            else -> R.drawable.torta_vainilla
        }
    }

    // === Postres individuales ===
    if (c.contains("postre") || c.contains("individual")) {
        return when {
            n.contains("mousse") -> R.drawable.mousse
            n.contains("tiramisu") -> R.drawable.tiramisu
            else -> R.drawable.mousse
        }
    }

    // === Productos sin azúcar ===
    if (c.contains("sin azúcar")) {
        return when {
            n.contains("naranja") -> R.drawable.torta_naranja
            n.contains("cheesecake") -> R.drawable.cheesecake_frutilla
            else -> R.drawable.torta_naranja
        }
    }

    // === Pastelería tradicional ===
    if (c.contains("pastelería")) {
        return when {
            n.contains("empanada") -> R.drawable.empanada_manzana
            n.contains("santiago") -> R.drawable.tarta_santiago
            else -> R.drawable.empanada_manzana
        }
    }

    // === Sin gluten ===
    if (c.contains("gluten")) {
        return when {
            n.contains("brownie") -> R.drawable.brownie
            n.contains("pan") -> R.drawable.pan_sin_gluten
            else -> R.drawable.brownie
        }
    }

    // === Veganos ===
    if (c.contains("vegano") || c.contains("vegana")) {
        return when {
            n.contains("chocolate") -> R.drawable.chocolate_vegano
            n.contains("galleta") -> R.drawable.galleta_vegana
            else -> R.drawable.chocolate_vegano
        }
    }

    // === Tortas especiales ===
    if (c.contains("especial")) {
        return when {
            n.contains("cumple") -> R.drawable.torta_hbd
            n.contains("boda") -> R.drawable.torta_boda
            else -> R.drawable.torta_hbd
        }
    }

    // DEFAULT
    return R.drawable.torta_chocolate
}