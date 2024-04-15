package com.example.patrol

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.patrol.logic.model.News
import com.example.patrol.view.news.NewsActivity
import com.example.patrol.view.news.NewsAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.button_news).setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }
        findViewById<CheckBox>(R.id.box_get_location).setOnClickListener {
            val checked = findViewById<CheckBox>(R.id.box_get_location).isChecked
            if (checked) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            } else {
                findViewById<EditText>(R.id.edit_place).setText("")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                handleLocationPermissionGranted()
            } else {
                handleLocationPermissionDenied()
            }
        }
    }

    private fun handleLocationPermissionGranted() {
        // Execute your code for using location here
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        getAddressFromLocation(location.latitude, location.longitude)
                    }
                }
        }
    }

    private fun handleLocationPermissionDenied() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                val addressFragments = arrayOf(address.featureName, address.thoroughfare, address.subLocality, address.locality, address.adminArea, address.countryName, address.postalCode)
                val completeAddress = addressFragments.filterNotNull().joinToString(separator = ", ")
                findViewById<EditText>(R.id.edit_place).setText(completeAddress)
            } else {
                findViewById<EditText>(R.id.edit_place).setText("No address found")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Unable geocode location", Toast.LENGTH_SHORT).show()
        }
    }

}