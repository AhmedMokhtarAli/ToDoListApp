package com.example.todolist.Ui.Fregments.Tasks


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.model.Task
import com.example.todolist.databinding.TaskItemBinding

class TaskAdapter(var context:Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var allTasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding,context)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = allTasks.get(position)
        holder.bindTask(task)
    }

    fun setAllTasks(allTasks: List<Task>) {
        this.allTasks = allTasks
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = allTasks.size

    class TaskViewHolder(val binding: TaskItemBinding,var context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindTask(task: Task) {
            binding.task=task
        }

    }
}