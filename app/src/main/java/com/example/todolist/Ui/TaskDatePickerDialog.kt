package com.example.todolist.Ui

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import java.util.*

class TaskDatePickerDialog(val context: Context?,var taskDate: Date) {
    private  var calendar:Calendar
    init {
        calendar= Calendar.getInstance()
    }
    fun creatDatePickerDialog(): DatePickerDialog? {

        val currentyear =calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it!!,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    taskDate = calendar.time
                },
                currentyear,
                currentMonth,
                currentDay
            )
        }

        return datePickerDialog
    }
    fun getDate() : Date{
        return taskDate
    }
}