package com.bitcode.sep_24_contentprovider_demo

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log

class ProductsProvider : ContentProvider(){

    private lateinit var db : SQLiteDatabase
    private var uriMatcher = UriMatcher(-1)

    override fun onCreate(): Boolean {
        db = ProductsDBHelper(
            context,
            "db_products",
            null, 1
        ).writableDatabase
        initUriMatcher()
        return true
    }

    private fun initUriMatcher(){
        uriMatcher.addURI("in.bitcode.products","products",1)
        uriMatcher.addURI("in.bitcode.products","products/#",2)
        uriMatcher.addURI("in.bitcode.products","products/electronics",3)
    }

    override fun query(
        uri : Uri,
        projection : Array<out String>?,
        selection : String?,
        selectionArgs : Array<out String>?,
        sortOrder : String?
    ): Cursor? {

        when(uriMatcher.match(uri)){
            1 -> {return db.query("products",projection,null,
                null,null,null,sortOrder)}
            2 -> {return db.query("products",projection,"id = ?",
                arrayOf(uri.pathSegments.get(1)),null,null,sortOrder)}
        }

        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
       Log.e("tag","insert Method Called")
        when (uriMatcher.match(uri)){
            1 -> {
                var rowNum = db.insert("products",null,values)
                Log.e("tag","rowNum -- $rowNum")
                if(rowNum.toInt() == -1){
                    return null
                }
                return Uri.withAppendedPath(
                    uri,
                    "${values?.getAsInteger("id")}"
                )
            }
        }
        return null
    }

    override fun delete(uri: Uri, selction: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)){
            1 -> return db.delete(
                "products",
                null,
                null
            )

            2 -> return db.delete(
                "products",
                "id = ?",
                arrayOf("${uri.pathSegments.get(1)}")
            )
        }
        return 0
    }

    override fun update(uri: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
       when (uriMatcher.match(uri)){
           1 -> {
               return db.update("products",p1,null,null)
           }

           2 -> {
               return db.update("products",p1,"id = ?", arrayOf("${uri.pathSegments.get(1)}"))
           }
       }
        return  0
    }
}