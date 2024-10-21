package com.example.mynotesapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynotesapp.db.DatabaseContract.NoteColumns.Companion._ID
import kotlin.jvm.Throws

class NoteHelper(context: Context) {

    private var dataBaseHelper: DatbaseHelper = DatbaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME

        // ,etode yang nantinya akan digunakan untuk menginisiasi database
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: NoteHelper(context)
            }
    }

    // metode untuk membuka dan menutup koneksi ke databasenya
    @Throws(SQLException::class)
    fun open(){
        database = dataBaseHelper.writableDatabase
    }

    fun close(){
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    /* metode untuk membuat crud */

    // metode untuk mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    // metode untuk mengambil data dengan id tertentu
    fun queryById(id : String): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null,
        )
    }

    // metode untuk meyimpan data
    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE, null, values)
    }

    // metode untuk memperbarui data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // metode untuk menghapus data
    fun deleteById(id: String): Int {
        return  database.delete(DATABASE_TABLE, "_ID = $id", null)
    }
}