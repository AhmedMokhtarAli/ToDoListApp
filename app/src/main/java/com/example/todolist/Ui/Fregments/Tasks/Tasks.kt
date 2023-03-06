package com.example.todolist.Ui.Fregments.Tasks

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Data.SortOrder
import com.example.todolist.R
import com.example.todolist.ViewModel.TaskViewModel
import com.example.todolist.databinding.FragmentTasksBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*


private const val TAG = "Tasks"
class Tasks : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        //init view model
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        //init recycler view and adapter
        val taskRecyclerView = binding.tasksRV
        adapter = TaskAdapter(requireContext())
        taskRecyclerView.adapter = adapter
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //show tasks in recyclerView
        taskViewModel.tasks.observe(viewLifecycleOwner){ tasks ->
            adapter.setAllTasks(tasks)
        }
        //add task
        binding.addTaskBtn.setOnClickListener(View.OnClickListener { findNavController().navigate(R.id.action_tasks_to_add) })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.delet -> {
                deletAllusers()
                true
            }
            R.id.praiorty -> {
                taskViewModel.onSortOrderSelected(SortOrder.BY_PRAIORTY)
                true
            }
            R.id.dateItem -> {
                taskViewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            else ->  return super.onOptionsItemSelected(item)
        }
    }

    private fun deletAllusers() {
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            taskViewModel.deleteAllTasks()
            Toast.makeText(requireContext(),"Tasks successfully removed", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delet tasks")
        builder.setMessage("Are you sure you want to delet all tasks ?")
        builder.create().show()
    }
}