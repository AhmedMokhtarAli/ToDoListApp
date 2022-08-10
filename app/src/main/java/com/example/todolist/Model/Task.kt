package com.example.todolist.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TaskTable")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var taskName: String,
    var startDate: Date,
    var endDate: Date,
    var completed: Boolean = false
)
