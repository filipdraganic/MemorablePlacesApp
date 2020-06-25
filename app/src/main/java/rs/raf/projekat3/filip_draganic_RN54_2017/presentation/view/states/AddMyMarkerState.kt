package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.states

sealed class AddMyMarkerState{
    object Success: AddMyMarkerState()
    data class Error(val message: String): AddMyMarkerState()
}