package com.example.todolist.Ui.Fregments.Update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.Ui.subFeature.Alarm
import com.example.todolist.ViewModel.TaskViewModel
import com.example.todolist.databinding.FragmentUpdateBinding
import com.example.todolist.model.Task
import java.util.*

class Update : Fragment(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private  var binding:FragmentUpdateBinding?=null
    private val args by navArgs<UpdateArgs>()
    private lateinit var taskViewModel: TaskViewModel

    private lateinit var date:Date
    private lateinit var calendar: Calendar
    private var year:Int=0
    private var month:Int=0
    private var day : Int=0
    private var hour : Int =0
    private var minutes: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUpdateBinding.inflate(inflater,container,false)

        taskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)

        showTaskInfo()

        binding?.dateBtnUpdate?.setOnClickListener(View.OnClickListener { pickDate() })
        binding?.updateTaskBtn?.setOnClickListener(View.OnClickListener {
            creatAlarmDialog()
            updateTask()
        })

        setHasOptionsMenu(true)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateTask() {
        val taskName = binding?.taskUpdateET?.editText?.text.toString()
        val taskDetials = binding?.taskDtialsUpdateET?.editText?.text.toString()

        if (checkTask(taskName, taskDetials)) {
            var updatedTask = Task(args.currentTask.id, taskName, taskDetials,date,3)
            taskViewModel.updateTask(updatedTask)
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
    private fun creatAlarmDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            val taskName=binding?.taskUpdateET?.editText?.text.toString()
            setAlarm(taskName)
            navigate()
        }
        builder.setNegativeButton("No") { _, _ ->
            navigate()
        }
        builder.setTitle("Set Alarm")
        builder.setMessage("Are you sure you want to update alarm for this task ?")
        builder.create().show()
    }
    fun setAlarm(taskName: String) {
        val alarm= Alarm(requireActivity())
        alarm.start(calendar,taskName)
    }
    fun navigate(){
        findNavController().navigate(R.id.action_update_to_tasks)
        Toast.makeText(requireContext(), "task successfully added", Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showTaskInfo() {
        date=args.currentTask.date
        getDateAndTime()

        binding?.taskUpdateET?.editText?.setText(args.currentTask.taskName)
        binding?.taskDtialsUpdateET?.editText?.setText(args.currentTask.taskDtailes)
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
    fun getDateAndTime(){
        calendar= Calendar.getInstance()
        calendar.time=date
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)
        day=calendar.get(Calendar.DAY_OF_MONTH)
        hour=calendar.get(Calendar.HOUR_OF_DAY)
        minutes=calendar.get(Calendar.MINUTE)
    }
    fun pickDate(){
        getDateAndTime()
        DatePickerDialog(requireContext(),this,year,month,day).show()
    }
    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day_of_month: Int) {
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,day_of_month)
        TimePickerDialog(requireContext(),this,hour,minutes,false).show()
    }

    override fun onTimeSet(timePicker : TimePicker?, hour: Int, minte: Int) {
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minte)
    }

}