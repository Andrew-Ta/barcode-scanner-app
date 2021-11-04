# barcode-scanner-app

## Description
- This is a simple barcode scanner app written in Kotlin. When the user starts the app they must allow the app to use their camera. Then the app will automatically start scanning for a barcode. When the view finder detects a barcode it will the app will change to the next screen and display all the product information related to that barcode. Users have the ability to save the item information that was scanned into the archive.

## Why I made this
- I made this mobile app to learn how to access camera permissions, find out how to convert a scanned barcode into data, and how to interface with an API in Kotlin using Android Studio.

## Limitations
- This app only works with products. If the user tries to scan a different type of barcode the app crashes.
- The focus of this project was on the functionality so the design isn't that pretty to look at.

## How to modify this app to work on your device
- In the ProductDatabase class file you will need to change line 24 to your own API key from "barcodelookup.com".
- In the Product class file you can uncomment the extra product parameters to suit your needs. You will also have to uncomment out the values you would like to retrieve in the ResultsListViewActivity.kt file. 

## Dependencies and Libraries used
- Code Scanner Library by [Yuriy-budiyev](https://github.com/yuriy-budiyev/code-scanner) based on the [ZXing](https://github.com/zxing/zxing) Library.
- [BarcodeLookup API](https://www.barcodelookup.com/api) to convert the barcode number to meaningful information.
- Retrofit
- Coroutines
- AndroidX Preferences Library

