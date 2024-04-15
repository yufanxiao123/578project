package com.example.patrol.view.crowd

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patrol.R
import com.example.patrol.logic.model.News
import com.example.patrol.view.news.NewsAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider

class CrowdActivity: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var heatmapTileProvider: HeatmapTileProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowd)
        val place = intent.getStringExtra("place")
        findViewById<TextView>(R.id.text_clowdness).text = "Crowdness at $place"
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val center = LatLng(
            intent.getDoubleExtra("lat", 0.0),
            intent.getDoubleExtra("lon", 0.0)
        )
        val zoomLevel = 17.0f
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel))
        mMap.addMarker(com.google.android.gms.maps.model.MarkerOptions().position(center).title("current location"))
        addHeatMap(listOf(
            LatLng(
                intent.getDoubleExtra("lat", 0.0),
                intent.getDoubleExtra("lon", 0.0)
            ),  // Example coordinates
            LatLng(
                intent.getDoubleExtra("lat", 0.0) + 0.0001,
                intent.getDoubleExtra("lon", 0.0) + 0.0001
            ),  // Example coordinates
            LatLng(
                intent.getDoubleExtra("lat", 0.0) - 0.0001,
                intent.getDoubleExtra("lon", 0.0) - 0.0001
            )  // Example coordinates
        ))
    }

    private fun addHeatMap(latLngs: List<LatLng> = listOf()) {
        heatmapTileProvider = HeatmapTileProvider.Builder()
            .data(latLngs)
            .build()

        mMap.addTileOverlay(TileOverlayOptions().tileProvider(heatmapTileProvider))
    }
}