package com.example.todolist.Repository

import androidx.lifecycle.LiveData
import com.example.todolist.Data.SortOrder
import com.example.todolist.Data.TaskDao
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(val taskDao: TaskDao) {

    //get tasks from Data Base
    fun getAllTasks(sortOrder: SortOrder):Flow<List<Task>> = taskDao.getAllTasks(sortOrder)

    //add new task
    suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    //update exitting task
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    //delete task
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    //delete allTasks
    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }
}