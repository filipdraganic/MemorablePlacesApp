package rs.raf.projekat3.filip_draganic_RN54_2017.presentation.recycler.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.memorableplacesapp.R
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarker
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.recycler.diff.MyMarkerDiff
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.recycler.viewholders.MyMarkerViewHolder
import rs.raf.projekat3.filip_draganic_RN54_2017.presentation.view.activity.EditMapsActivity
import timber.log.Timber

class MyMarkerAdapter(myMarkerDiff: MyMarkerDiff) :ListAdapter<MyMarker, MyMarkerViewHolder>(myMarkerDiff) {


    override fun onBindViewHolder(holder: MyMarkerViewHolder, position: Int) {

        val marker = getItem(position)

        holder.itemView.setOnClickListener(View.OnClickListener {
            Timber.e(marker.title)


            val intent = Intent(holder.containerView.context, EditMapsActivity::class.java)

            intent.putExtra("myMarker", marker)

            holder.containerView.context.startActivity(intent)


        })

        holder.bind(marker)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMarkerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.savedlocation_item, parent, false)
        return MyMarkerViewHolder(containerView)
    }


}