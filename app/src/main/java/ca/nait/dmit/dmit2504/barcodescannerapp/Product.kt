package ca.nait.dmit.dmit2504.barcodescannerapp

import com.squareup.moshi.Json

data class Product(
    var barcode_number: String,
    var barcode_formats: String,
    var mpn: String,
    var model: String,
//    var asin: String,
    var title: String,
    var category: String,
    var manufacturer: String,
    var brand: String,
//    var contributors: String,
//    var age_group: String,
//    var ingredients: String,
//    var nutrition_facts: String,
//    var energy_efficiency_class: String,
    var color: String,
//    var gender: String,
//    var material: String,
//    var pattern: String,
//    var format: String,
//    var multipack: String,
//    var size: String,
//    var length: String,
//    var width: String,
//    var height: String,
//    var weight: String,
    var release_date: String,
    var last_update: String,
    var description: String,
//    var features: String,
//    var images: String,
//    var stores: String,
//    var reviews: String
)