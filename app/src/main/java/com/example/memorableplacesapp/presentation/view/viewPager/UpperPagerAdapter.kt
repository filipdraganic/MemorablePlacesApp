package com.example.memorableplacesapp.presentation.view.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.memorableplacesapp.presentation.view.fragments.MapsFragment
import com.example.memorableplacesapp.presentation.view.fragments.SavedLocationsFragment

class UpperPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object{
        const val MAPS = 0
        const val SAVED_LOCATIONS = 1
        const val COUNT = 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            MAPS -> MapsFragment()
            else -> SavedLocationsFragment()
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            MAPS -> "Maps"
            else -> "Saved Locations"
        }
    }

}