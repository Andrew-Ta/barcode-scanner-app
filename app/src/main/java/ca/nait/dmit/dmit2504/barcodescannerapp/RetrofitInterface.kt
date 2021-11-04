package ca.nait.dmit.dmit2504.barcodescannerapp

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("products")
    suspend fun fetchBarcodeResponseData(
        @Query("barcode") barcode: String,
        @Query("key") apiKey: String
    ) : BarcodeLookupResponse

}