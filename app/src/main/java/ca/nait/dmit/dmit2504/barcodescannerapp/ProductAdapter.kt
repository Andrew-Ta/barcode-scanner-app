package ca.nait.dmit.dmit2504.barcodescannerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

class ProductAdapter: BaseAdapter() {
    private val productInfoList = mutableListOf<Product>()

    override fun getCount(): Int {
        return  productInfoList.size
    }

    override fun getItem(position: Int): Any {
        return productInfoList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflatedView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_product, parent, false)

        // Find all the views in the list item
        val barcodeNumberTextView: TextView = inflatedView.findViewById(R.id.list_item_barcode_number_textView)
        val barcodeFormatTextView: TextView = inflatedView.findViewById(R.id.list_item_barcode_format_textView)
        val mpnTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val modelTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val titleTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val categoryTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val manufacturerTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val brandTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val colorTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val releaseDateTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val lastUpdateTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)
        val descriptionTextView: TextView = inflatedView.findViewById(R.id.list_item_mpn_textView)

        // Set the text for each view in the list item
        barcodeNumberTextView.text = "Barcode Number: ${productInfoList[position].barcode_number}"
        barcodeFormatTextView.text = "Barcode Format(s): ${productInfoList[position].barcode_formats}"
        mpnTextView.text = "MPN: ${productInfoList[position].mpn}"
        modelTextView.text = "Model: ${productInfoList[position].model}"
        titleTextView.text = "Title: ${productInfoList[position].title}"
        categoryTextView.text = "Category: ${productInfoList[position].category}"
        manufacturerTextView.text = "Manufacturer: ${productInfoList[position].manufacturer}"
        brandTextView.text = "Brand: ${productInfoList[position].brand}"
        colorTextView.text = "Color: ${productInfoList[position].color}"
        releaseDateTextView.text = "Release Date: ${productInfoList[position].release_date}"
        lastUpdateTextView.text = "Last Update: ${productInfoList[position].last_update}"
        descriptionTextView.text = "Description: ${productInfoList[position].description}"

        return inflatedView
    }

    fun addResponseProduct(responseProduct: Product){
        productInfoList.add(responseProduct)
    }


}