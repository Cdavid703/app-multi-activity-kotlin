package com.example.appmultiactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnInicio: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnInicio = findViewById(R.id.btnInicio)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnInicio.setOnClickListener {

            val usuario = etUsuario.text.toString().trim()
            val password = etContrasena.text.toString().trim()

            if (usuario == "hz" && password == "123") {

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.popup_titulo))
                    .setMessage(getString(R.string.bienvenido_hz))
                    .setPositiveButton(getString(R.string.boton_aceptar)) { dialog, _ ->
                        dialog.dismiss()

                        val intent = Intent(this, Activity3::class.java)
                        startActivity(intent)
                    }
                    .show()

            } else {

                etUsuario.setText("")
                etContrasena.setText("")

                etUsuario.error = getString(R.string.datos_incorrectos)
            }
        }

        btnRegistrar.setOnClickListener {

            val intent = Intent(this, Activity4::class.java)
            startActivity(intent)

        }
    }
}