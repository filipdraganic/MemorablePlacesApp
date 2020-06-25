package rs.raf.projekat3.filip_draganic_RN54_2017.application

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat3.filip_draganic_RN54_2017.modules.coreModule
import rs.raf.projekat3.filip_draganic_RN54_2017.modules.myMarkerModule
import timber.log.Timber

class MemorablePlacesApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        initTimber()
        initKoin()
        initStetho()
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
        Timber.e("Inicijalizovan Timber")

    }

    private fun initKoin(){
        val modules = listOf(
            coreModule,
            myMarkerModule


        )
        startKoin {
            androidLogger(Level.DEBUG)
            // Use application context
            androidContext(this@MemorablePlacesApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
        Timber.e("Pokrenut Koin")
    }


    private fun initStetho(){
        Stetho.initializeWithDefaults(this)
        Timber.e("Pokrenut Stetho")

    }

}