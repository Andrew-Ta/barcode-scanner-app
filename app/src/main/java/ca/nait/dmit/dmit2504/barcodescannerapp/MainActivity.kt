package ca.nait.dmit.dmit2504.barcodescannerapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import retrofit2.*
import java.util.*

private const val CAMERA_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    val scanButton: Button by lazy{findViewById(R.id.activity_main_scan_button)}
    val scannerView: CodeScannerView by lazy{findViewById(R.id.activity_main_scanner_view)}
    val resultTextView: TextView by lazy{findViewById(R.id.activity_main_scan_results_textview)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
        codeScanner()


        //click the scan button to start scanning for a new barcode
        //not in use currently
        scanButton.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun codeScanner() {
        var barcodeNumber: String

        codeScanner = CodeScanner(this, scannerView)
        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            //set ScanMode to continuous: keep scanning for barcodes continuously
            //set ScanMode to single: stop scanning after first barcode
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            //set true = flash will be set turned on when the app starts
            isFlashEnabled = false

            codeScanner.startPreview()

            decodeCallback = DecodeCallback {

                barcodeNumber = it.text.toString()
                resultTextView.text = barcodeNumber

                val intent = Intent(this@MainActivity, ResultsListView::class.java)
                if(!barcodeNumber.isNullOrBlank()){
                    val bundle = Bundle()
                    bundle.putString("barcode", barcodeNumber)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@MainActivity,"Error with barcode number",Toast.LENGTH_SHORT).show()
                }
            }
            errorCallback = ErrorCallback {
                Log.e("Main", "Camera initialization error: ${it.message}")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You need to grant camera permissions to use this app", Toast.LENGTH_SHORT).show()
                } else {
                    //user granted camera permissions
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_main_menu, menu)

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
}