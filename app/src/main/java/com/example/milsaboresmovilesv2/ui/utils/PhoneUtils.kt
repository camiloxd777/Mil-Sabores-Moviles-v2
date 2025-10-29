package com.example.milsaboresmovilesv2.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File


fun openCamera(context: Context) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    // Verificar si hay app de cámara disponible
    if (intent.resolveActivity(context.packageManager) != null) {
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        Toast.makeText(context, "No se encontró cámara disponible", Toast.LENGTH_SHORT).show()
    }
}




fun openEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun openMaps(context: Context, address: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // Fallback: abrir en navegador
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}")
        )
        context.startActivity(webIntent)
    }
}

fun openPhoneDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    // Verificar si hay alguna aplicación que pueda manejar la acción
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // Fallback: mostrar mensaje o alternativa
        android.widget.Toast.makeText(
            context,
            "No se encontró aplicación de teléfono",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}
