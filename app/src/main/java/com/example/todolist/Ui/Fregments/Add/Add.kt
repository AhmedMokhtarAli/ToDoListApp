
package com.example.todolist.Ui.Fregments.Add

import android.app.*
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.model.Task
import com.example.todolist.R
import com.example.todolist.Ui.subFeature.Alarm
import com.example.todolist.Ui.subFeature.TaskDatePickerDialog
import com.example.todolist.ViewModel.TaskViewModel
import com.example.todolist.databinding.FragmentAddBinding
import java.util.*

private const val TAG = "Add TASK"

class Add : Fragment() ,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentAddBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var task: Task
    private lateinit var praiorties: Array<String>
    private var praiortySelected = 3

    private lateinit var calendar: Calendar

    private var year:Int=0
    private var month:Int=0
    private var day : Int=0
    private var hour : Int =0
    private var minutes: Int = 0
    private var date=Date()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        pickDate()

        binding.praiortyTV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position).toString()
                praiortySelected = getPraiorty(item)
            }


        binding.saveTask.setOnClickListener({
            creatAlarmDialog(calendar)
            saveTask()
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        praiorties = resources.getStringArray(R.array.praiorties)
        val praiortiesAdapter = ArrayAdapter(requireContext(), R.layout.drop_dwon_item, praiorties)
        binding.praiortyTV.setAdapter(praiortiesAdapter)
    }

    fun getDateAndTime() {
        calendar = Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)
        day=calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minutes = calendar.get(Calendar.MINUTE)


        date=calendar.time
        Log.i(TAG, "getDateAndTime: ${calendar.time}")

    }
    fun pickDate(){
        binding.dateBtn.setOnClickListener {
            getDateAndTime()
            Log.i(TAG, "pickDate: ${calendar.time}")
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun saveTask() {
        val taskName = binding.taskET.editText?.text.toString()
        val taskDetials = binding.taskDtialsET.editText?.text.toString()


        if (checkTask(taskName, taskDetials)) {
            task = Task(0, taskName, taskDetials, date, praiortySelected)
            taskViewModel.addTask(task)
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

    fun getPraiorty(item: String): Int {
        when (item) {
            "Low" -> return 1
            "Medium" -> return 2
            else -> return 3
        }
    }

    private fun creatAlarmDialog(calendar: Calendar) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            setAlarm(calendar)
            navigate()
        }
        builder.setNegativeButton("No") { _, _ ->
            navigate()
        }
        builder.setTitle("Set Alarm")
        builder.setMessage("Are you sure you want to set alarm for this task ?")
        builder.create().show()
    }
    fun setAlarm(calendar: Calendar) {
        val alarm=Alarm(requireActivity())
        alarm.start(calendar)
    }
    fun navigate(){
        findNavController().navigate(R.id.action_add_to_tasks)
        Toast.makeText(requireContext(), "task successfully added", Toast.LENGTH_LONG).show()
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day_of_month: Int) {
        calendar.set(Calendar.YEAR,year)
        calendar.set(Calendar.MONTH,month)
        calendar.set(Calendar.DAY_OF_MONTH,day_of_month)
        Log.i(TAG, "onDateSet: ${calendar.time}")
        TimePickerDialog(requireContext(),this,hour,minutes,false).show()
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minte: Int) {
        calendar.set(Calendar.HOUR,hour)
        calendar.set(Calendar.MINUTE,minte)
        calendar.set(Calendar.SECOND,0)
        date=calendar.time
        Log.i(TAG, "onTimeSet: ${calendar.time}")
    }
}