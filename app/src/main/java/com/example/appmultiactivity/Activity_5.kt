package com.example.appmultiactivity

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream

class Activity5 : AppCompatActivity() {

    private lateinit var imageViewFoto: ImageView
    private lateinit var btnAbrirCamara: Button
    private lateinit var btnGuardarGaleria: Button

    private var photoUri: Uri? = null
    private var photoFile: File? = null

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && photoUri != null) {
                imageViewFoto.setImageURI(photoUri)
            } else {
                Toast.makeText(this, getString(R.string.mensaje_error_foto), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_5)

        imageViewFoto = findViewById(R.id.imageViewFoto)
        btnAbrirCamara = findViewById(R.id.btnAbrirCamara)
        btnGuardarGaleria = findViewById(R.id.btnGuardarGaleria)

        btnAbrirCamara.setOnClickListener {
            abrirCamara()
        }

        btnGuardarGaleria.setOnClickListener {
            guardarEnGaleria()
        }
    }

    private fun abrirCamara() {
        photoFile = File.createTempFile(
            "foto_app_",
            ".jpg",
            cacheDir
        )

        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile!!
        )

        // 🔥 SOLUCIÓN DEL ERROR AQUÍ
        cameraLauncher.launch(photoUri!!)
    }

    private fun guardarEnGaleria() {
        val file = photoFile

        if (file == null || !file.exists()) {
            Toast.makeText(this, getString(R.string.mensaje_primero_foto), Toast.LENGTH_SHORT).show()
            return
        }

        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "foto_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AppMultiActivity")
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        if (uri != null) {
            resolver.openOutputStream(uri)?.use { outputStream ->
                FileInputStream(file).use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Toast.makeText(this, getString(R.string.mensaje_foto_guardada), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.mensaje_error_foto), Toast.LENGTH_SHORT).show()
        }
    }
}