package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memorableplacesapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_edit_maps.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.contract.MyMarkerContract
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.viewmodel.MyMarkerViewModel

class EditMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val myMarkerViewModel : MyMarkerContract.ViewModel by viewModel<MyMarkerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        init()
    }


    private fun init(){
        val myMarker : MyMarker = intent.extras?.get("myMarker") as MyMarker

        titleET.setText(myMarker.title)
        noteET.setText(myMarker.note)

        mMap.addMarker(MarkerOptions().position(LatLng(myMarker.latitude, myMarker.longitude)).title(myMarker.title).snippet(myMarker.note))
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(LatLng(myMarker.latitude, myMarker.longitude), 15F)))

        initListeners(myMarker)
    }

    private fun initListeners(myMarker : MyMarker){

        odustaniBtn.setOnClickListener{
            finish()
        }

        deleteBtn.setOnClickListener {
            myMarkerViewModel.delete(myMarker)
            finish()
        }

        sacuvajBtn.setOnClickListener {
            myMarker.title = titleET.text.toString()
            myMarker.note = noteET.text.toString()
            myMarkerViewModel.updateMyMarker(myMarker)
            finish()
        }

    }
}