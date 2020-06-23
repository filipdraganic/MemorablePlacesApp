package rs.raf.filip_draganic_RN54_2017.presentation.recycler.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.savedlocation_item.*
import rs.raf.filip_draganic_RN54_2017.data.models.MyMarker

class MyMarkerViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer{




    fun bind(myMarker: MyMarker){

        titleTV.text = myMarker.title
        noteTV.text = myMarker.note
        dateTV.text = myMarker.date.toString()



    }

}