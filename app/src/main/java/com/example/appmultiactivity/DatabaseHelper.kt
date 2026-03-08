package com.example.appmultiactivity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USUARIOS = "usuarios"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_APELLIDO = "apellido"
        const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_APELLIDO TEXT NOT NULL,
                $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    fun insertarUsuario(id: Int, nombre: String, apellido: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ID, id)
            put(COL_NOMBRE, nombre)
            put(COL_APELLIDO, apellido)
            put(COL_PASSWORD, password)
        }

        val resultado = db.insert(TABLE_USUARIOS, null, values)
        db.close()
        return resultado != -1L
    }

    fun consultarUsuario(id: Int): Cursor {
        val db = readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COL_ID = ?",
            arrayOf(id.toString())
        )
    }

    fun actualizarUsuario(id: Int, nombre: String, apellido: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_APELLIDO, apellido)
            put(COL_PASSWORD, password)
        }

        val filas = db.update(
            TABLE_USUARIOS,
            values,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
        return filas > 0
    }

    fun eliminarUsuario(id: Int): Boolean {
        val db = writableDatabase
        val filas = db.delete(
            TABLE_USUARIOS,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
        return filas > 0
    }
}