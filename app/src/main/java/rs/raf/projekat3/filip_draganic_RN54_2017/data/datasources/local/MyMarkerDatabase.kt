package rs.raf.projekat3.filip_draganic_RN54_2017.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat3.filip_draganic_RN54_2017.data.extra.Converters
import rs.raf.projekat3.filip_draganic_RN54_2017.data.models.MyMarkerEntity


@Database(
    entities = [MyMarkerEntity::class],
    version = 2,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyMarkerDatabase : RoomDatabase(){
    abstract fun myMarkerDao(): MyMarkerDao
}