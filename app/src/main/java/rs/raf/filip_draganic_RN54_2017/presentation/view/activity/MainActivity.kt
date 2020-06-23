package rs.raf.filip_draganic_RN54_2017.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memorableplacesapp.R
import rs.raf.filip_draganic_RN54_2017.presentation.view.viewPager.UpperPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e("Uspelo?")
        init()
    }

    private fun init(){


        viewPager.adapter =
            UpperPagerAdapter(
                supportFragmentManager
            )
        tabLayout.setupWithViewPager(viewPager)

    }



}