package com.bitcode.sep_24_contentprovider_demo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ProductsDBHelper(
    context: Context?,
    dbName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int) : SQLiteOpenHelper(context,dbName,factory,version) {

    //onCreate and onUpgrade -- these methods are mandatory to be implemented as
                           // SQLiteOpenHelper is an abstract class
    override fun onCreate(db: SQLiteDatabase?) {
       Log.e("tag","onCreate Method Called")
        db!!.execSQL("create table products(id integer primary key," +
                "title text," +
                "price integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    private fun addProducts(db : SQLiteDatabase?){
        var values = ContentValues()
        values.put("id",101)
        values.put("title","Macbook")
        values.put("price",150000)

        var rowNum = db!!.insert("products", null, values)

        var values1 = ContentValues()
        values1.put("id",102)
        values1.put("title","HP Laptop")
        values1.put("price",130000)

        var rowNum1 = db.insert("products",null,values1)

        var values2 = ContentValues()
        values2.put("id",103)
        values2.put("title","Dell Laptop")
        values2.put("price", 90000)

        var rowNum2 = db.insert("products",null,values2)

        Log.e("tag","RowNum -- $rowNum2")
    }
}