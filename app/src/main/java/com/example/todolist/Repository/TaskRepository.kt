package com.example.todolist.Repository

import androidx.lifecycle.LiveData
import com.example.todolist.Data.TaskDao
import com.example.todolist.Model.Task

class TaskRepository(val taskDao: TaskDao) {

    //get tasks from Data Base
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    //add new task
    suspend fun addTask(task: Task) {
        taskDao.inserTask(task)
    }

    //update exitting task
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    //delete task
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}