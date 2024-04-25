package com.example.patrol.view.crowd

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patrol.R
import com.example.patrol.logic.model.News
import com.example.patrol.view.news.NewsAdapter
import com.example.patrol.view.news.NewsViewModel
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

    // cgz added- viewModel
    private val viewModel by lazy { ViewModelProvider(this).get(CrowdViewModel::class.java) }


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


        // cgz added
        val latLngs: MutableList<LatLng> = mutableListOf()
//        viewModel.getCrowd("-47.36688677728133","126.00817028056607")
        viewModel.getCrowd(center.latitude.toString(),center.longitude.toString())
        viewModel.crowdLiveData.observe(this, Observer{ result ->
            val crowds = result.getOrNull()
            if (crowds != null) {
                addHeatMap(crowds)
                for (crowd in crowds){
                    latLngs.add(crowd)
                    Log.d("cg",center.latitude.toString())
                    Log.d("cg",center.longitude.toString())
                }


            } else {
                Toast.makeText(this, "no news found", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })




//        yufanxiao original code
//        addHeatMap(listOf(
//            LatLng(
//                intent.getDoubleExtra("lat", 0.0),
//                intent.getDoubleExtra("lon", 0.0)
//            ),  // Example coordinates
//            LatLng(
//                intent.getDoubleExtra("lat", 0.0) + 0.0001,
//                intent.getDoubleExtra("lon", 0.0) + 0.0001
//            ),  // Example coordinates
//            LatLng(
//                intent.getDoubleExtra("lat", 0.0) - 0.0001,
//                intent.getDoubleExtra("lon", 0.0) - 0.0001
//            )  // Example coordinates
//        ))
    }

    private fun addHeatMap(latLngs: List<LatLng> = listOf()) {
        heatmapTileProvider = HeatmapTileProvider.Builder()
            .data(latLngs)
            .build()

        mMap.addTileOverlay(TileOverlayOptions().tileProvider(heatmapTileProvider))
    }
}