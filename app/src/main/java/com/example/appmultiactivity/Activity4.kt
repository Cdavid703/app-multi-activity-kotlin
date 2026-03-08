package com.example.appmultiactivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Activity4 : AppCompatActivity() {

    private lateinit var etIdRegistro: EditText
    private lateinit var etNombreRegistro: EditText
    private lateinit var etApellidoRegistro: EditText
    private lateinit var etPasswordRegistro: EditText
    private lateinit var btnGuardarRegistro: Button

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)

        etIdRegistro = findViewById(R.id.etIdRegistro)
        etNombreRegistro = findViewById(R.id.etNombreRegistro)
        etApellidoRegistro = findViewById(R.id.etApellidoRegistro)
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro)
        btnGuardarRegistro = findViewById(R.id.btnGuardarRegistro)

        databaseHelper = DatabaseHelper(this)

        btnGuardarRegistro.setOnClickListener {
            guardarUsuario()
        }
    }

    private fun guardarUsuario() {
        val idTexto = etIdRegistro.text.toString().trim()
        val nombre = etNombreRegistro.text.toString().trim()
        val apellido = etApellidoRegistro.text.toString().trim()
        val password = etPasswordRegistro.text.toString().trim()

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
            mostrarPopup("No se pudo registrar el usuario")
        }
    }

    private fun limpiarCampos() {
        etIdRegistro.setText("")
        etNombreRegistro.setText("")
        etApellidoRegistro.setText("")
        etPasswordRegistro.setText("")
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