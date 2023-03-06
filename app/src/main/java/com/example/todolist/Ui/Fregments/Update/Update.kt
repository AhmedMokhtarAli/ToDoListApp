package com.example.todolist.Ui.Fregments.Update

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.model.Task
import com.example.todolist.R
import com.example.todolist.Ui.TaskDatePickerDialog
import com.example.todolist.ViewModel.TaskViewModel
import com.example.todolist.databinding.FragmentUpdateBinding
import java.util.*

class Update : Fragment() {
    private  var binding:FragmentUpdateBinding?=null
    private val args by navArgs<UpdateArgs>()
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var datePickerDialog:TaskDatePickerDialog


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUpdateBinding.inflate(inflater,container,false)

        taskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)

        showTaskInfo()

        datePickerDialog=TaskDatePickerDialog(context,args.currentTask.date)


        binding?.dateBtnUpdate?.setOnClickListener(View.OnClickListener { datePickerDialog.creatDatePickerDialog()?.show() })
        binding?.updateTaskBtn?.setOnClickListener(View.OnClickListener {
            updateTask(datePickerDialog.getDate())
        })

        setHasOptionsMenu(true)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateTask(date: Date) {
        val taskName = binding?.taskUpdateET?.editText?.text.toString()
        val taskDetials = binding?.taskDtialsUpdateET?.editText?.text.toString()
        val hour=binding!!.taskTimeUpdate.hour
        val mintes=binding!!.taskTimeUpdate.minute

        if (checkTask(taskName, taskDetials)) {
            var updatedTask = Task(args.currentTask.id, taskName, taskDetials,date,hour,mintes,3)
            taskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "task successfully updated", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_update_to_tasks)
        } else {
            Toast.makeText(requireContext(), "Please fill all fildes", Toast.LENGTH_LONG).show()
        }
    }
    private fun checkTask(taskName: String, taskDetails: String): Boolean {
        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(taskDetails)) {
            return false
        }
        return true
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun showTaskInfo()
    {
        binding?.taskUpdateET?.editText?.setText(args.currentTask.taskName)
        binding?.taskDtialsUpdateET?.editText?.setText(args.currentTask.taskDtailes)
        binding?.taskTimeUpdate?.hour=args.currentTask.hour
        binding?.taskTimeUpdate?.minute=args.currentTask.mintes
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.task_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.delet)
        {
            deletTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletTask() {
        val bulider = AlertDialog.Builder(requireContext())
        bulider.setPositiveButton("Yes") { _, _ ->
            taskViewModel.deleteTask(args.currentTask)
            Toast.makeText(requireContext(),"Task successfully removed",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_update_to_tasks)
        }
        bulider.setNegativeButton("No"){_,_ ->}
        bulider.setTitle("Delet ${args.currentTask.taskName} task")
        bulider.setMessage("Are you sure you want to delet ${args.currentTask.taskName} ?")
        bulider.create().show()
    }

}