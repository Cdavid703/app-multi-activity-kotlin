package com.example.appmultiactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Activity3 : AppCompatActivity() {

    private lateinit var etIdCrud: EditText
    private lateinit var etNombreCrud: EditText
    private lateinit var etApellidoCrud: EditText
    private lateinit var etPasswordCrud: EditText

    private lateinit var btnInsertarCrud: Button
    private lateinit var btnLeerCrud: Button
    private lateinit var btnModificarCrud: Button
    private lateinit var btnEliminarCrud: Button
    private lateinit var btnIrCamara: Button

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        etIdCrud = findViewById(R.id.etIdCrud)
        etNombreCrud = findViewById(R.id.etNombreCrud)
        etApellidoCrud = findViewById(R.id.etApellidoCrud)
        etPasswordCrud = findViewById(R.id.etPasswordCrud)

        btnInsertarCrud = findViewById(R.id.btnInsertarCrud)
        btnLeerCrud = findViewById(R.id.btnLeerCrud)
        btnModificarCrud = findViewById(R.id.btnModificarCrud)
        btnEliminarCrud = findViewById(R.id.btnEliminarCrud)
        btnIrCamara = findViewById(R.id.btnIrCamara)

        databaseHelper = DatabaseHelper(this)

        btnInsertarCrud.setOnClickListener {
            insertarUsuario()
        }

        btnLeerCrud.setOnClickListener {
            leerUsuario()
        }

        btnModificarCrud.setOnClickListener {
            modificarUsuario()
        }

        btnEliminarCrud.setOnClickListener {
            eliminarUsuario()
        }

        btnIrCamara.setOnClickListener {
            startActivity(Intent(this, Activity5::class.java))
        }
    }

    private fun insertarUsuario() {
        val idTexto = etIdCrud.text.toString().trim()
        val nombre = etNombreCrud.text.toString().trim()
        val apellido = etApellidoCrud.text.toString().trim()
        val password = etPasswordCrud.text.toString().trim()

        if (idTexto.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || password.isEmpty()) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        val id = idTexto.toInt()

        val insertado = databaseHelper.insertarUsuario(id, nombre, apellido, password)

        if (insertado) {
            mostrarPopup(getString(R.string.registro_exitoso))
            limpiarCampos()
        } else {
            mostrarPopup("No se pudo insertar el registro")
        }
    }

    private fun leerUsuario() {
        val idTexto = etIdCrud.text.toString().trim()

        if (idTexto.isEmpty()) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        val id = idTexto.toInt()
        val cursor = databaseHelper.consultarUsuario(id)

        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOMBRE))
            val apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_APELLIDO))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PASSWORD))

            etNombreCrud.setText(nombre)
            etApellidoCrud.setText(apellido)
            etPasswordCrud.setText(password)

            mostrarPopup("Registro encontrado")
        } else {
            mostrarPopup(getString(R.string.registro_no_encontrado))
        }

        cursor.close()
    }

    private fun modificarUsuario() {
        val idTexto = etIdCrud.text.toString().trim()
        val nombre = etNombreCrud.text.toString().trim()
        val apellido = etApellidoCrud.text.toString().trim()
        val password = etPasswordCrud.text.toString().trim()

        if (idTexto.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || password.isEmpty()) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        val id = idTexto.toInt()
        val actualizado = databaseHelper.actualizarUsuario(id, nombre, apellido, password)

        if (actualizado) {
            mostrarPopup(getString(R.string.registro_actualizado))
            limpiarCampos()
        } else {
            mostrarPopup(getString(R.string.registro_no_encontrado))
        }
    }

    private fun eliminarUsuario() {
        val idTexto = etIdCrud.text.toString().trim()

        if (idTexto.isEmpty()) {
            mostrarPopup(getString(R.string.error_campos))
            return
        }

        val id = idTexto.toInt()
        val eliminado = databaseHelper.eliminarUsuario(id)

        if (eliminado) {
            mostrarPopup(getString(R.string.registro_eliminado))
            limpiarCampos()
        } else {
            mostrarPopup(getString(R.string.registro_no_encontrado))
        }
    }

    private fun limpiarCampos() {
        etIdCrud.setText("")
        etNombreCrud.setText("")
        etApellidoCrud.setText("")
        etPasswordCrud.setText("")
    }

    private fun mostrarPopup(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.popup_titulo))
            .setMessage(mensaje)
            .setPositiveButton(getString(R.string.boton_aceptar)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}