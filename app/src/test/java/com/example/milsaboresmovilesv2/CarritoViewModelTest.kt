package com.example.milsaboresmovilesv2

import com.example.milsaboresmovilesv2.viewmodel.CarritoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CarritoViewModelTest : StringSpec({

    lateinit var viewModel: CarritoViewModel

    beforeTest {
        viewModel = CarritoViewModel()
    }

    "añadir un producto nuevo lo agrega a la lista" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")

        viewModel.items.size shouldBe 1
        viewModel.items.first().nombre shouldBe "Torta de Chocolate"
        viewModel.items.first().cantidad shouldBe 1
    }

    "añadir un producto existente incrementa la cantidad" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")

        viewModel.items.size shouldBe 1
        viewModel.items.first().cantidad shouldBe 2
    }

    "incrementar la cantidad de un producto" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.inc("Torta de Chocolate")

        viewModel.items.first().cantidad shouldBe 2
    }

    "decrementar la cantidad de un producto" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.dec("Torta de Chocolate")

        viewModel.items.first().cantidad shouldBe 1
    }

    "decrementar la cantidad a cero elimina el producto" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.dec("Torta de Chocolate")

        viewModel.items.size shouldBe 0
    }

    "eliminar un producto del carrito" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.remove("Torta de Chocolate")

        viewModel.items.size shouldBe 0
    }

    "limpiar el carrito" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.add("Torta de Manjar", "Exquisita torta", 0, "12000")
        viewModel.clear()

        viewModel.items.size shouldBe 0
    }

    "calcular el total del carrito" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000") // 1 * 10000
        viewModel.add("Torta de Manjar", "Exquisita torta", 0, "12000")    // 1 * 12000
        viewModel.inc("Torta de Chocolate") // 2 * 10000

        viewModel.total() shouldBe 32000 // (2 * 10000) + 12000
    }

    "calcular el total de items del carrito" {
        viewModel.add("Torta de Chocolate", "Deliciosa torta", 0, "10000")
        viewModel.add("Torta de Manjar", "Exquisita torta", 0, "12000")
        viewModel.inc("Torta de Chocolate")

        viewModel.totalItems() shouldBe 3
    }

})