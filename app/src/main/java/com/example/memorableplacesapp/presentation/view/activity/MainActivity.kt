package com.example.memorableplacesapp.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.memorableplacesapp.R
import com.example.memorableplacesapp.presentation.view.viewPager.UpperPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e("Uspelo?")
        init()
    }

    private fun init(){


        viewPager.adapter = UpperPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

    }



}