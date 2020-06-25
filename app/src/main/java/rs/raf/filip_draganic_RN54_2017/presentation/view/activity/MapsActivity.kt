package rs.raf.filip_draganic_RN54_2017.presentation.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.memorableplacesapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.filip_draganic_RN54_2017.presentation.contract.MyMarkerContract
import rs.raf.filip_draganic_RN54_2017.presentation.viewmodel.MyMarkerViewModel
import timber.log.Timber
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1


    }

    private lateinit var mMap: GoogleMap

    private var map: GoogleMap? = null

    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    private var cameraPosition: CameraPosition? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private val myMarkerViewModel : MyMarkerContract.ViewModel by viewModel<MyMarkerViewModel>()

    private var myMarkerLat:Double? = null
    private var myMarkerLng:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        init()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    private fun init(){

        initListeners()
    }

    private fun initListeners(){
        odustaniBtn.setOnClickListener {
            val intent = Intent(this,
                MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        sacuvajBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            if(myMarkerLat != null && myMarkerLng != null){
                val date = Calendar.getInstance().time
                myMarkerViewModel.insert(MyMarker(0, titleET.text.toString(), noteET.text.toString(), myMarkerLat!!, myMarkerLng!!, date))

            }

            startActivity(intent)
            finish()
        }
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

        this.map = googleMap

        getLocationPermission()

    }




    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            updateLocationUI()
            getDeviceLocation()


        }
        else {
            Timber.e("Usao u else")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    updateLocationUI()
                    getDeviceLocation()


                }
                else{
                    Toast.makeText(this, "This app needs location permission, shutting down now.", Toast.LENGTH_LONG).show()
                    Timber.e("Denied")
                    finish()
                }

            }
        }
    }


    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Timber.e("Exception:%s", e)
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                            map?.addMarker(MarkerOptions().position(LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)).title("Trenutna lokacija"))
                            myMarkerLat = lastKnownLocation!!.latitude
                            myMarkerLng = lastKnownLocation!!.longitude
                        }
                    } else {
                        Timber.e("Current location is null. Using defaults.")
                        Timber.e( "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Timber.e("Exception: %s"+ e.message, e)
        }
    }








}