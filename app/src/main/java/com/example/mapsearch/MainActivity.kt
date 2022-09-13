package com.example.mapsearch


import android.content.ContentValues.TAG
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.io.IOException
import java.util.*


class MainActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null

//    var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.key_map), Locale.US)
        }
//        searchView = findViewById(R.id.searchView)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))


// todo show this on map (24.856652685322413, 67.03025384477505)


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                latLong(place)

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })



//        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//
//                val location = searchView?.query.toString()
//
//
//                var addressList: List<Address>? = null
//
//
//                val geocoder = Geocoder(this@MainActivity)
//                try {
//
//                    addressList = geocoder.getFromLocationName(location, 1)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//
//                val address = addressList!![0]
//
//
//                val latLng = LatLng(address.latitude, address.longitude)
//
//
//                mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
//
//
//                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50f))
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    fun latLong(place:Place){

        val location = place.name


        var addressList: List<Address>? = null


        val geocoder = Geocoder(this@MainActivity)
        try {

            addressList = geocoder.getFromLocationName(location, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val address = addressList!![0]


        val latLng = LatLng(address.latitude, address.longitude)


        mMap!!.addMarker(MarkerOptions().position(latLng).title(location))


        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50f))
    }


}
