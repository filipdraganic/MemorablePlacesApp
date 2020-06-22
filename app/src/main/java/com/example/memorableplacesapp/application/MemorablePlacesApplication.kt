package com.example.memorableplacesapp.application

import android.app.Application
import timber.log.Timber

class MemorablePlacesApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        initTimber()
        Timber.e("Inicijalizovan Timber")
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }

}