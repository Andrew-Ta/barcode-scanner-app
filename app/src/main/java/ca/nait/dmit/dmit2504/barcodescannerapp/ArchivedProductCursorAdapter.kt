package ca.nait.dmit.dmit2504.barcodescannerapp

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cursoradapter.widget.CursorAdapter

class ArchivedProductCursorAdapter(context: Context, cursor: Cursor, flags: Int) : CursorAdapter(context,cursor,flags) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.list_item_archived_product, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val archivedProductIdTextView: TextView = view?.findViewById(R.id.list_item_archived_id)!!
        val archivedProductDataTextView: TextView = view?.findViewById(R.id.list_item_archived_textview)!!
        val deleteButton: Button = view?.findViewById(R.id.list_item_archived_product_delete_button)!!

        val archivedProductId: Int = cursor?.getInt(cursor?.getColumnIndexOrThrow(BaseColumns._ID)) ?: 0
        archivedProductIdTextView.setText("${archivedProductId}")

        val archivedProductData: String = cursor?.getString(cursor?.getColumnIndexOrThrow(ProductDatabase.TABLE_PRODUCTS_COLUMN_DATA)) ?: "no entry was found"
        archivedProductDataTextView.setText("${archivedProductData}")

        deleteButton.setOnClickListener { view: View ->
            val db = ProductDatabase(context!!)
            db.deleteListItem(archivedProductId.toLong())
            Toast.makeText(context!!,"Deleted entry #${archivedProductId}", Toast.LENGTH_SHORT).show()
            changeCursor(db.listSavedProducts)
            notifyDataSetChanged()
        }
    }
}