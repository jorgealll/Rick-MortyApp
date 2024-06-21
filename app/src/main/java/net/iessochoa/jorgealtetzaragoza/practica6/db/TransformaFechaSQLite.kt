package net.iessochoa.jorgealtetzaragoza.practica6.db

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

class TransformaFechaSQLite {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        //le quitamos la hora, pero si la necesitárais, no llaméis a removeTime
                return if (date == null) null else removeTime(date).time
    }
    fun removeTime(date: Date?): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }
}