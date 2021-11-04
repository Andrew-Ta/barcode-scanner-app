package ca.nait.dmit.dmit2504.barcodescannerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.barcodelookup.com/v3/"
//CHANGE TO YOUR OWN API KEY!
private const val API_KEY = "4b4snqnh9uu81wojhixk94b00lbjsh"

class ResultsListView : AppCompatActivity() {

    private val productDatabase = ProductDatabase(this)
    //test box text view!
    private val testResponseTextView: TextView by lazy{findViewById(R.id.activity_results_list_TESTBOX_textView)}

    override fun onCreate(savedInstanceState: Bundle?) {

        val resultsListView : ListView by lazy {findViewById(R.id.activity_results_list_view_listView)}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results_list_view)

        if(intent != null && intent.extras != null){
            val bundle = intent.extras
            val barcodeNumber = bundle?.getString("barcode").toString()
//            val networkAPI = NetworkAPI()

            testResponseTextView.text = "Barcode Number: ${barcodeNumber}"

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val retrofitApiService = retrofit.create(RetrofitInterface::class.java)

            CoroutineScope(Dispatchers.IO).launch {

                //using NetworkAPI to retrieve JSON String *NOT IN USE*
//                val getUrlString = "${BASE_URL}products?barcode=${barcodeNumber}&key=${API_KEY}"
//                val responseData = networkAPI.fetchUrlResponseString(getUrlString)

                //using the retrofitInterface to retrieve JSON Object
                val barcodeLookupResponse = retrofitApiService.fetchBarcodeResponseData(barcodeNumber, API_KEY)

                withContext(Dispatchers.Main) {

                    //Uncomment out the values you would like to retrieve from BarcodeLookup API
                    val returnedBarcodeNumber = barcodeLookupResponse.products[0].barcode_number.toString()
                    val returnedBarcodeFormat = barcodeLookupResponse.products[0].barcode_formats.toString()
                    val returnedMpn = barcodeLookupResponse.products[0].mpn.toString()
                    val returnedModel = barcodeLookupResponse.products[0].model.toString()
//                    val returnedAsin = barcodeLookupResponse.products[0].asin.toString()
                    val returnedTitle = barcodeLookupResponse.products[0].title.toString()
                    val returnedCategory = barcodeLookupResponse.products[0].category.toString()
                    val returnedManufacturer = barcodeLookupResponse.products[0].manufacturer.toString()
                    val returnedBrand = barcodeLookupResponse.products[0].brand.toString()
//                    val returnedContributors = barcodeLookupResponse.products[0].contributors.toString()
//                    val returnedAgeGroup = barcodeLookupResponse.products[0].age_group.toString()
//                    val returnedIngredients = barcodeLookupResponse.products[0].ingredients.toString()
//                    val returnedNutritionalFacts = barcodeLookupResponse.products[0].nutrition_facts.toString()
//                    val returnedEnergyEfficiencyClass = barcodeLookupResponse.products[0].energy_efficiency_class.toString()
                    val returnedColor = barcodeLookupResponse.products[0].color.toString()
//                    val returnedGender = barcodeLookupResponse.products[0].gender.toString()
//                    val returnedMaterial = barcodeLookupResponse.products[0].material.toString()
//                    val returnedPattern = barcodeLookupResponse.products[0].pattern.toString()
//                    val returnedFormat = barcodeLookupResponse.products[0].format.toString()
//                    val returnedMultipack = barcodeLookupResponse.products[0].multipack.toString()
//                    val returnedSize = barcodeLookupResponse.products[0].size.toString()
//                    val returnedLength = barcodeLookupResponse.products[0].length.toString()
//                    val returnedWidth = barcodeLookupResponse.products[0].width.toString()
//                    val returnedHeight = barcodeLookupResponse.products[0].height.toString()
//                    val returnedWeight = barcodeLookupResponse.products[0].weight.toString()
                    val returnedReleaseDate = barcodeLookupResponse.products[0].release_date.toString()
                    val returnedLastUpdate = barcodeLookupResponse.products[0].last_update.toString()
                    val returnedDescription = barcodeLookupResponse.products[0].description.toString()
//                    val returnedFeatures = barcodeLookupResponse.products[0].features.toString()
//                    val returnedImages = barcodeLookupResponse.products[0].images.toString()
//                    val returnedStores = barcodeLookupResponse.products[0].stores.toString()
//                    val returnedReviews = barcodeLookupResponse.products[0].reviews.toString()

                    val responseProduct = Product(
                        barcode_number = returnedBarcodeNumber,
                        barcode_formats = returnedBarcodeFormat,
                        mpn = returnedMpn,
                        model = returnedModel,
//                        asin = returnedAsin,
                        title = returnedTitle,
                        category =returnedCategory,
                        manufacturer = returnedManufacturer,
                        brand = returnedBrand,
//                        contributors = returnedContributors,
//                        age_group = returnedAgeGroup,
//                        ingredients = returnedIngredients,
//                        nutrition_facts =returnedNutritionalFacts,
//                        energy_efficiency_class = returnedEnergyEfficiencyClass,
                        color = returnedColor,
//                        gender = returnedGender,
//                        material = returnedMaterial,
//                        pattern = returnedPattern,
//                        format = returnedFormat,
//                        multipack = returnedMultipack,
//                        size = returnedSize,
//                        length = returnedLength,
//                        width = returnedWidth,
//                        height = returnedHeight,
//                        weight = returnedWeight,
                        release_date = returnedReleaseDate,
                        last_update = returnedLastUpdate,
                        description = returnedDescription
//                        features = returnedFeatures,
//                        images = returnedImages,
//                        stores = returnedStores,
//                        reviews = returnedReviews
                    )
                    // Show a Toast message when its completed
                    Toast.makeText(this@ResultsListView, "GET successful.", Toast.LENGTH_SHORT).show()

                    testResponseTextView.text = "Barcode Number: ${ responseProduct.barcode_number }\n"
                        .plus("Barcode Format(s): ${ responseProduct.barcode_formats }\n")
                        .plus("MPN: ${ responseProduct.mpn }\n")
                        .plus("Model: ${ responseProduct.model }\n")
                        .plus("Title: ${ responseProduct.title }\n")
                        .plus("Category: ${ responseProduct.category }\n")
                        .plus("Manufacturer: ${ responseProduct.manufacturer }\n")
                        .plus("Brand: ${ responseProduct.brand }\n")
                        .plus("Color: ${ responseProduct.color }\n")
                        .plus("Release Date: ${ responseProduct.release_date }\n")
                        .plus("Last Update: ${ responseProduct.last_update }\n")
                        .plus("Description: ${ responseProduct.description }\n")

                    //POPULATE LIST VIEW *NOT USING*
//                    val productAdapter = ProductAdapter()
//                    productAdapter.addResponseProduct(responseProduct)
//
//                    resultsListView.adapter = productAdapter

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_results_listview_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item_archived_items -> {
                val ArchivedProductActivityIntent = Intent(this, ArchivedProductActivity::class.java)
                startActivity(ArchivedProductActivityIntent)
            }
        }
        return true
    }

    fun onClickScanAgain(view: View) {
        val MainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(MainActivityIntent)
    }

    fun onClickSave(view: View) {
        val productData = testResponseTextView.text.toString()
        val newProductData = ArchivedProduct()
        newProductData.data = productData

        if(productData.isNullOrBlank()){
            Toast.makeText(this, "No data to save", Toast.LENGTH_LONG).show()
        }else{
            productDatabase.insertProduct(newProductData)
            Toast.makeText(this, "Entry was archived successfully!", Toast.LENGTH_LONG).show()
            testResponseTextView.setText("")
        }

    }
}