package com.example.memorableplacesapp.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memorableplacesapp.R
import timber.log.Timber

class SplashActivity : AppCompatActivity(R.layout.activity_splash){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init(){
        Timber.e("Zavrsio?")

        val intent = Intent(this, MapsActivity::class.java)

        startActivity(intent)

        finish()

    }


}