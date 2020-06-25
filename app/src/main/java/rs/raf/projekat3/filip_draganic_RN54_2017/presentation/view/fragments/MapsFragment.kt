package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.contract.MyMarkerContract
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.states.MyMarkerState
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.viewmodel.MyMarkerViewModel
import timber.log.Timber

class MapsFragment : Fragment() {
    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1


        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        private const val M_MAX_ENTRIES = 5
    }
    private var map: GoogleMap? = null

    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    private var cameraPosition: CameraPosition? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private val myMarkerViewModel : MyMarkerContract.ViewModel by viewModel<MyMarkerViewModel>()



    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        this.map = googleMap

//        this.map?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
//            override fun getInfoContents(p0: Marker?): View {
//
//            }
//
//            override fun getInfoWindow(p0: Marker?): View? {
//                return null
//            }
//        })

        getLocationPermission()

        updateLocationUI()

        getDeviceLocation()

        initObservers()

        myMarkerViewModel.getAll()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        fusedLocationProviderClient = context?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        mapFragment?.getMapAsync(callback)
    }

    private fun initObservers(){
        Timber.e("Init Observers")

        myMarkerViewModel.myMarkerState.observe(viewLifecycleOwner, Observer{
            renderState(it)
        })

    }

    private fun renderState(myMarkerState : MyMarkerState){
        Timber.e("Render state")
        when(myMarkerState){
            is MyMarkerState.Success ->{
                for(marker in myMarkerState.markers){
                    map?.addMarker(MarkerOptions().position(LatLng(marker.latitude, marker.longitude)).title(marker.title).snippet(marker.note))

                }
            }
        }

    }

    private fun getLocationPermission() {
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true
        }
        else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when(requestCode){
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }


    private fun updateLocationUI(){
        if (map == null){
            return
        }
        try {
            if(locationPermissionGranted){
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            }else{
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException){
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun getDeviceLocation(){

        try{
            if(locationPermissionGranted){

                val locationResult = fusedLocationProviderClient.lastLocation
                    activity?.let {
                        locationResult.addOnCompleteListener(it) { task ->
                            if(task.isSuccessful){

                                lastKnownLocation = task.result
                                if (lastKnownLocation != null){
                                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude),
                                        DEFAULT_ZOOM.toFloat()))
                                }

                        }else{
                                Timber.e("Current location is null. Using defaults.")
                                Timber.e("Exception "+ task.exception)

                                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                                map?.uiSettings?.isMyLocationButtonEnabled = false
                            }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }

    }



}