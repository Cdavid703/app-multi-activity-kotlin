package com.example.appmultiactivity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class Activity6 : AppCompatActivity() {

    private lateinit var imgUsuarioApi: ImageView
    private lateinit var tvNombreApi: TextView
    private lateinit var tvCorreoApi: TextView
    private lateinit var tvCiudadApi: TextView
    private lateinit var btnCargarUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_6)

        imgUsuarioApi = findViewById(R.id.imgUsuarioApi)
        tvNombreApi = findViewById(R.id.tvNombreApi)
        tvCorreoApi = findViewById(R.id.tvCorreoApi)
        tvCiudadApi = findViewById(R.id.tvCiudadApi)
        btnCargarUsuario = findViewById(R.id.btnCargarUsuario)

        btnCargarUsuario.setOnClickListener {
            consumirApi()
        }
    }

    private fun consumirApi() {
        val url = "https://randomuser.me/api/"

        tvNombreApi.text = "Cargando..."
        tvCorreoApi.text = "Cargando..."
        tvCiudadApi.text = "Cargando..."

        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    val user = results.getJSONObject(0)

                    val nameObject = user.getJSONObject("name")
                    val fullName =
                        nameObject.getString("first") + " " + nameObject.getString("last")

                    val email = user.getString("email")

                    val locationObject = user.getJSONObject("location")
                    val city = locationObject.getString("city")

                    val pictureObject = user.getJSONObject("picture")
                    val imageUrl = pictureObject.getString("large")

                    tvNombreApi.text = fullName
                    tvCorreoApi.text = email
                    tvCiudadApi.text = city

                    Picasso.get().load(imageUrl).into(imgUsuarioApi)

                } catch (e: Exception) {
                    Toast.makeText(this, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}