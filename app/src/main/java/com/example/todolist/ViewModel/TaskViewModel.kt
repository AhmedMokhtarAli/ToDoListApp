package com.example.todolist.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.Data.PreferencesManager
import com.example.todolist.Data.SortOrder
import com.example.todolist.Data.TaskDao
import com.example.todolist.Data.TasksDataBase
import com.example.todolist.model.Task
import com.example.todolist.Repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val taskRepository: TaskRepository
    val taskDao: TaskDao
    val preferencesManager:PreferencesManager

    init {
        taskDao = TasksDataBase.getTasksDataBase(application).taskDao()
        taskRepository = TaskRepository(taskDao)
        preferencesManager= PreferencesManager(application.applicationContext)
    }

    val preferencesFlow=preferencesManager.preferencesFlow

     private val taskFlow= combine(preferencesFlow){}.flatMapLatest {
        taskDao.getAllTasks(preferencesFlow.first().sortOrder)
    }

     val tasks= taskFlow.asLiveData()

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

    fun deleteAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteAllTasks()
        }
    }

    fun onSortOrderSelected(sortOrder: SortOrder)=viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }
}