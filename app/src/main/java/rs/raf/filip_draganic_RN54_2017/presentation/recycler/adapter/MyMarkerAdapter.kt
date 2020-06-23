package rs.raf.filip_draganic_RN54_2017.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.memorableplacesapp.R
import kotlinx.android.synthetic.main.savedlocation_item.*
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.filip_draganic_RN54_2017.presentation.recycler.diff.MyMarkerDiff
import rs.raf.filip_draganic_RN54_2017.presentation.recycler.viewholders.MyMarkerViewHolder
import timber.log.Timber

class MyMarkerAdapter(myMarkerDiff: MyMarkerDiff) :ListAdapter<MyMarker, MyMarkerViewHolder>(myMarkerDiff) {


    override fun onBindViewHolder(holder: MyMarkerViewHolder, position: Int) {

        val marker = getItem(position)

        holder.itemView.setOnClickListener(View.OnClickListener {
            Timber.e(marker.title)
        })

        holder.bind(marker)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMarkerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.savedlocation_item, parent, false)
        return MyMarkerViewHolder(containerView)
    }


}