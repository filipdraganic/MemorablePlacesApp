package rs.raf.filip_draganic_RN54_2017.presentation.view.states

import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker


sealed class MyMarkerState {

    object Update: MyMarkerState()
    data class Success(val markers: List<MyMarker>): MyMarkerState()
    data class Error(val message: String): MyMarkerState()
}