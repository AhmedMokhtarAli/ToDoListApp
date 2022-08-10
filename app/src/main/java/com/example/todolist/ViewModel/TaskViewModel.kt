package com.example.todolist.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.Data.TaskDao
import com.example.todolist.Data.TasksDataBase
import com.example.todolist.Model.Task
import com.example.todolist.Repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val taskRepository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val taskDao: TaskDao

    init {
        taskDao = TasksDataBase.getTasksDataBase(application).taskDao()
        taskRepository = TaskRepository(taskDao)
        allTasks = taskRepository.allTasks
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }
}