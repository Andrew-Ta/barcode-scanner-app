package ca.nait.dmit.dmit2504.barcodescannerapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class ProductDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "producthistory.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_PRODUCTS_NAME = "products"
        const val TABLE_PRODUCTS_COLUMN_DATA = "data"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ${TABLE_PRODUCTS_NAME} (${BaseColumns._ID} INTEGER PRIMARY KEY, ${TABLE_PRODUCTS_COLUMN_DATA} TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE ${TABLE_PRODUCTS_NAME}")
        onCreate(db)
    }

    val listSavedProducts : Cursor
        get() {
            val db = readableDatabase
            val queryStatement = "SELECT ${BaseColumns._ID}, ${TABLE_PRODUCTS_COLUMN_DATA} FROM ${TABLE_PRODUCTS_NAME} ORDER BY ${BaseColumns._ID} DESC"

            return db.rawQuery(queryStatement, null)
        }

    fun insertProduct(newProductEntry: ArchivedProduct) : Long {
        val db = writableDatabase
        val columnValues = contentValuesOf().apply {
            put(TABLE_PRODUCTS_COLUMN_DATA, newProductEntry.data)
        }

        return db.insert(TABLE_PRODUCTS_NAME, null, columnValues)
    }

    fun deleteListItem(id: Long): Int {
        return writableDatabase.delete(TABLE_PRODUCTS_NAME, BaseColumns._ID + " = ?", arrayOf(id.toString()))
    }
}