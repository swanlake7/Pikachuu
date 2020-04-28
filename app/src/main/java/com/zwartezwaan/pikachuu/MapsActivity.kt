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
import com.google.android.gms.maps.model.BitmapDescriptor
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
        LoadPoky()
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
            locatie!!.longitude= 0.0 }

        override fun onLocationChanged(p0: Location?) {
           locatie= p0 }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("Not yet implemented") }

        override fun onProviderEnabled(provider: String?) {
            TODO("Not yet implemented") }

        override fun onProviderDisabled(provider: String?) {
            TODO("Not yet implemented") }
    }

var oldLocatie:Location?=null
    inner class mijnDraad:Thread{
        constructor():super(){
            oldLocatie = Location("Start! heh")
            oldLocatie!!.longitude = 0.0
            oldLocatie!!.latitude = 0.0
        }

        override fun run(){
            while(true){
                try{
                    if(oldLocatie!!.distanceTo(locatie) == 0f){ continue}
                    
                    runOnUiThread{
                        mMap!!.clear()
                        // Add a marker in Sydney and move the camera
                    // for displaying the user
val sydney = LatLng(locatie!!.latitude, locatie!!.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(sydney)
                            .title("Pikachuu")
                            .snippet("my location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pikapikachu))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5f))

                    //for showing pokemons
                        for(p in 0..pokemonyList.size-1){
                               var newP = pokemonyList[p]
                                if(newP.caught==false){
                                    var pLocatie = LatLng(newP.lat!!, newP.long!!)
                                    mMap!!.addMarker(MarkerOptions()
                                        .position(pLocatie)
                                        .title(newP.name)
                                        .snippet(newP.desc)
                                        .icon(BitmapDescriptorFactory.fromResource(newP.img!!))
                                    )
                                }
                        }

                    }
                    Thread.sleep(1000)
                }catch(ex:Exception){

                }
            }
        }
    }

var pokemonyList = ArrayList<Pokemony>()

    fun LoadPoky(){
        pokemonyList.add( Pokemony(R.drawable.kirby, "Kirbyy", "Kirrbyyy",
            30.0, 44.8737, -0.5546)  )
        pokemonyList.add( Pokemony(R.drawable.itachii, "Itachi",
            "The Hero We Do Not Deserve", 70.0, 34.4682, 135.5320)  )
        pokemonyList.add( Pokemony(R.drawable.peach,
            "Peach", "Princess Peach", 40.0, 59.2325, 18.0760)  )
        pokemonyList.add( Pokemony(R.drawable.assassin,
            "Assassin", "My DNA is the Future", 88.0,
            31.6034, 34.8710)  )
        pokemonyList.add( Pokemony(R.drawable.zelda,
            "Princess Zelda", "Elf Princess of the Forest", 68.0, 44.4525, 26.0608)  )
        pokemonyList.add( Pokemony(R.drawable.kyloren,
            "Kylo Ren", "Prince of Darkness", 78.0, 62.0410, 73.3467)  )
        pokemonyList.add( Pokemony(R.drawable.joker,
            "The Joker", "Why So Seriouss?? ", 55.0, 40.8198, -73.8576)  )
    }


}
