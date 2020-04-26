package com.zwartezwaan.pikachuu

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPerm()
    }

    //for dangerous permission (only needed for ver 23 or later)

    var AccessLocation = 123
    fun checkPerm(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
            {requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), AccessLocation )
                return }
        }
        //otherwise older versions won't require further so just run the below func
        getUserLocation()
    }

    fun getUserLocation(){
        Toast.makeText(this, "location access on! ", Toast.LENGTH_LONG).show()
            var mijnLocatie = MoiLocLis()
            var locatieManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locatieManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, mijnLocatie)
            var moiThread= mijnDraad()
        moiThread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {AccessLocation ->
            {
             if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {getUserLocation()}
                else{Toast.makeText(this, "we could not access your location", Toast.LENGTH_LONG).show()  }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

var locatie:Location?=null
    //get user location
    inner class MoiLocLis:LocationListener{

        constructor(){
            locatie= Location("Start!!")
            locatie!!.latitude= 0.0
            locatie!!.longitude= 0.0
        }
        override fun onLocationChanged(p0: Location?) {
           locatie= p0
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("Not yet implemented")
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("Not yet implemented")
        }
    }

    inner class mijnDraad:Thread{
        constructor():super(){}

        override fun run(){
            while(true){
                try{
                    runOnUiThread{
                        mMap!!.clear()
                        // Add a marker in Sydney and move the camera
                        val sydney = LatLng(locatie!!.latitude, locatie!!.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(sydney)
                            .title("Pikachuu")
                            .snippet("my location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pikapikachu))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5f))
                    }
                    Thread.sleep(1000)
                }catch(ex:Exception){

                }
            }
        }
    }





}
