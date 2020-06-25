package rs.raf.projekat3.filip_draganic_RN54_2017.data.extra

import androidx.room.TypeConverter
import java.util.*

class Converters {
    init {

    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return if (value == null) Date() else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}