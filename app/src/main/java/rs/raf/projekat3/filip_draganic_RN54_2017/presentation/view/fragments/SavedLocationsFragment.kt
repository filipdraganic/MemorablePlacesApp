package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorableplacesapp.R
import kotlinx.android.synthetic.main.savedlocation_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.contract.MyMarkerContract
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.recycler.adapter.MyMarkerAdapter
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.recycler.diff.MyMarkerDiff
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.states.MyMarkerState
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.viewmodel.MyMarkerViewModel
import timber.log.Timber

class SavedLocationsFragment : Fragment(R.layout.savedlocation_fragment){

    private lateinit var myMarkersAdapter: MyMarkerAdapter

    val myMarkerViewModel : MyMarkerContract.ViewModel by sharedViewModel<MyMarkerViewModel>()

    private var fabGore = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init(){
        initRecycler()
        initListeners()
        initObservers()

        Timber.e("Saved lokacija fragment init")

    }


    private fun initObservers(){
        Timber.e("Observer u lokacija fragment")

        myMarkerViewModel.myMarkerState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            Timber.e("RENDEROVAO SAM")
            renderState(it)
            markerRV.scrollToPosition(0)
        })


        if (fabGore == true){
             FAB.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
        }
        else{

            FAB.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
        }

        myMarkerViewModel.getSearchQuery(searchET.text.toString(), fabGore)


    }

    private fun renderState(myMarkerState: MyMarkerState){

        when(myMarkerState){
            is MyMarkerState.Success ->{
                myMarkersAdapter.submitList(myMarkerState.markers)
            }
            is MyMarkerState.Error -> {
                Toast.makeText(context, "ERROR: "+myMarkerState.message,Toast.LENGTH_LONG ).show()
            }

        }

    }

    private fun initListeners(){
        FAB.setOnClickListener{
            if (fabGore == true){
                fabGore = false
                FAB.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
            else{
                fabGore = true
                FAB.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            }
            myMarkerViewModel.getSearchQuery(searchET.text.toString(), fabGore)

        }

        searchET.doAfterTextChanged {
            myMarkerViewModel.getSearchQuery(searchET.text.toString(), fabGore)
        }
    }

    private fun initRecycler(){
        markerRV.layoutManager = LinearLayoutManager(activity)
        myMarkersAdapter = MyMarkerAdapter(MyMarkerDiff())
        markerRV.adapter = myMarkersAdapter
    }

}