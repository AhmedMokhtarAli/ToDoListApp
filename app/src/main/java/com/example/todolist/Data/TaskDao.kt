package com.example.todolist.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    fun getAllTasks(sortOrder: SortOrder): Flow<List<Task>>{
        when(sortOrder){
            SortOrder.BY_DATE -> return getAllTasksByDate()
            SortOrder.BY_PRAIORTY -> return getAllTasksByPraiorty()
        }
    }

    @Query("SELECT * FROM TaskTable ORDER BY date DESC ")
    fun getAllTasksByDate(): Flow<List<Task>>

    @Query("SELECT * FROM TaskTable ORDER BY praiorty DESC ")
    fun getAllTasksByPraiorty(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("delete from TaskTable")
    suspend fun deleteAllTasks()
}