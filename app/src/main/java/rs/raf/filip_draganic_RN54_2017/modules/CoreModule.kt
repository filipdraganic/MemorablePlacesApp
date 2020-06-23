package rs.raf.filip_draganic_RN54_2017.modules

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import rs.raf.filip_draganic_RN54_2017.data.datasources.local.MyMarkerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.*

val coreModule = module {
    single{ Room.databaseBuilder(androidContext(), MyMarkerDatabase::class.java, "MyMarkerdb")
        .fallbackToDestructiveMigration()
        .build()
    }

    single { createMoshi()}
}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}