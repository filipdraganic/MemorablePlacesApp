package rs.raf.filip_draganic_RN54_2017.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.filip_draganic_RN54_2017.presentation.view.states.AddMyMarkerState
import rs.raf.filip_draganic_RN54_2017.presentation.view.states.MyMarkerState

class MyMarkerContract {

    interface ViewModel{
        val myMarkerState: LiveData<MyMarkerState>
        val addDone: LiveData<AddMyMarkerState>


        fun getAll()
        fun insert(myMarker: MyMarker)
        fun getSearchQuery(searchQuery: String, fabGore: Boolean)
        fun updateMyMarker(myMarker: MyMarker)
        fun delete(myMarker: MyMarker)

    }
}