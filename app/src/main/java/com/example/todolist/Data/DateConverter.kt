package com.example.todolist.Data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimeStampToDate(timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }

    @TypeConverter
    fun fromDateToTimeStamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}