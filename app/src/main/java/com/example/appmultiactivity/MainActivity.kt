package com.example.appmultiactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNombreUsuario: EditText
    private lateinit var etResultadoSaludo: EditText
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombreUsuario = findViewById(R.id.etNombreUsuario)
        etResultadoSaludo = findViewById(R.id.etResultadoSaludo)
        btnContinuar = findViewById(R.id.btnContinuar)

        btnContinuar.setOnClickListener {
            val nombreUsuario = etNombreUsuario.text.toString().trim()

            if (nombreUsuario.isEmpty()) {
                etNombreUsuario.error = getString(R.string.error_campos)
            } else {
                val saludoPopup = getString(R.string.saludo_popup, nombreUsuario)
                val saludoEditText = getString(R.string.saludo_edittext, nombreUsuario)

                etResultadoSaludo.setText(saludoEditText)

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.popup_titulo))
                    .setMessage(saludoPopup)
                    .setPositiveButton(getString(R.string.boton_aceptar)) { dialog, _ ->
                        dialog.dismiss()

                        val intent = Intent(this, Activity2::class.java)
                        intent.putExtra("nombreUsuario", nombreUsuario)
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }
}