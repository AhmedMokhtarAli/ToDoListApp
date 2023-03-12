package com.example.todolist.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
@Entity(tableName = "TaskTable")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var taskName: String,
    var taskDtailes: String,
    var date: Date,
    var praiorty:Int,
    var completed: Boolean = false
):Parcelable