package rs.raf.filip_draganic_RN54_2017.presentation.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker

class MyMarkerDiff : DiffUtil.ItemCallback<MyMarker>(){


    override fun areItemsTheSame(oldItem: MyMarker, newItem: MyMarker): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: MyMarker, newItem: MyMarker): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude &&
                oldItem.date == newItem.date &&
                oldItem.note == newItem.note &&
                oldItem.title == newItem.title

    }


}