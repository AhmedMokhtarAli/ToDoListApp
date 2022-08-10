package com.example.todolist.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.Model.Task

@TypeConverters(DateConverter::class)
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TasksDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        var INSTENCE: TasksDataBase? = null

        fun getTasksDataBase(context: Context): TasksDataBase {
            if (INSTENCE != null) {
                return INSTENCE as TasksDataBase
            }
            synchronized(this)
            {
                INSTENCE = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDataBase::class.java,
                    "TaskDataBase"
                ).build()
            }
            return INSTENCE as TasksDataBase
        }
    }
}