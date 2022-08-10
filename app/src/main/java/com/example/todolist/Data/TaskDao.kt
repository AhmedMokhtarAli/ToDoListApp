package com.example.todolist.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.Model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskTable ORDER BY startDate asc ")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}