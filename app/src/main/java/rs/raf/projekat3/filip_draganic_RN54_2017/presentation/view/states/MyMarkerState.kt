package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.states

import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarker


sealed class MyMarkerState {

    object Delete: MyMarkerState()
    object Update: MyMarkerState()
    data class Success(val markers: List<MyMarker>): MyMarkerState()
    data class Error(val message: String): MyMarkerState()
}