package rs.raf.filip_draganic_RN54_2017.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MyMarker (
    val id: Long,
    var title: String,
    var note: String,
    val latitude: Double,
    val longitude: Double,
    val date: Date
):Parcelable