package com.a60n1.kohabarna

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context) : SQLiteOpenHelper(context, DB_name, null, 1) {
    companion object {
        const val DB_name = "barna.db "
        const val TB_name = "Barna "
        const val id = "ID"
        const val emri = "B_emri"
        const val pershkrimi = "B_pershkrim"
        const val dataFillim = "B_dataF"
        const val dataMbarim = "B_dataM"
        const val doktorri = "B_doktori"
        const val alerts = "B_alerts"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TB_name(ID INTEGER PRIMARY KEY AUTOINCREMENT, B_emri TEXT, B_pershkrim TEXT, B_dataF TEXT, B_dataM TEXT, B_doktori TEXT, B_alerts TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TB_name")
    }

    fun deleteData(id: String): Int = this.writableDatabase.delete(TB_name, "id = ?", arrayOf(id))
    val dataGet: Cursor get() = this.writableDatabase.rawQuery("SELECT * FROM $TB_name", null)
    fun addData(
        emri_text: String,
        pershkrimi_text: String,
        dataF_text: String,
        dataM_text: String,
        dok_text: String,
        alerts_text: String
    ) {
        val values = ContentValues()
        values.put(emri, emri_text)
        values.put(pershkrimi, pershkrimi_text)
        values.put(dataFillim, dataF_text)
        values.put(dataMbarim, dataM_text)
        values.put(doktorri, dok_text)
        values.put(alerts, alerts_text)
        this.writableDatabase.insert(TB_name, null, values)
    }
}