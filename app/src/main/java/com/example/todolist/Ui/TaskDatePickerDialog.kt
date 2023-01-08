package com.example.todolist.Ui

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import java.util.*

class TaskDatePickerDialog(val context: Context?) {
    private  var calendar:Calendar
    private var date:Date
    init {
        calendar= Calendar.getInstance()
        date=calendar.time
    }
    fun creatDatePickerDialog(): DatePickerDialog? {

        val year =calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dpd = context?.let {
            DatePickerDialog(
                it!!,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    date = calendar.time
                },
                year,
                month,
                day
            )
        }

        return dpd
    }
    fun getDate() : Date{
        return date
    }

    //update date
    fun setOldeDate(date:Date)
    {
        this.date=date
    }
    fun updateDate(date : Date):Date
    {
        if (date==this.date)
            return date
        return this.date
    }
}