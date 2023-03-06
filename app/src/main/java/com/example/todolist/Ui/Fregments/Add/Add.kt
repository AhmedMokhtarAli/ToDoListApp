package com.example.todolist.Ui.Fregments.Add

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.model.Task
import com.example.todolist.R
import com.example.todolist.Ui.AlarmReciver
import com.example.todolist.Ui.TaskDatePickerDialog
import com.example.todolist.ViewModel.TaskViewModel
import com.example.todolist.databinding.FragmentAddBinding


class Add : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var task: Task
    private lateinit var praiorties: Array<String>
    private var praiortySelected = 3
    private lateinit var taskDatePickerDialog: TaskDatePickerDialog

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private lateinit var calendar: Calendar
    private var hour: Int = 0
    private var minutes: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        getTime()

        taskDatePickerDialog = TaskDatePickerDialog(context, Calendar.getInstance().time)
        binding.dateBtn.setOnClickListener(View.OnClickListener {
            taskDatePickerDialog.creatDatePickerDialog()?.show()
        })

        binding.praiortyTV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position).toString()
                praiortySelected = getPraiorty(item)
            }


        binding.saveTask.setOnClickListener(View.OnClickListener {
            creatAlarmDialog()
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

    fun getTime() {
        calendar = Calendar.getInstance()
        hour = binding.taskTime.hour
        minutes = binding.taskTime.minute

        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minutes
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun saveTask() {

        val taskName = binding.taskET.editText?.text.toString()
        val taskDetials = binding.taskDtialsET.editText?.text.toString()
        val date = taskDatePickerDialog.getDate()
        getTime()

        if (checkTask(taskName, taskDetials)) {
            task = Task(0, taskName, taskDetials, date, hour, minutes, praiortySelected)
            taskViewModel.addTask(task)
            Toast.makeText(requireContext(), "task successfully added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_add_to_tasks)
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

    private fun creatAlarmDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            setAlarm()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Set Alarm")
        builder.setMessage("Are you sure you want to set alarm for this task ?")
        builder.create().show()
    }

    fun setAlarm() {
        alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReciver::class.java)
        pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
        creatNotification()
    }

    fun creatNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "ToDoReminder"
            val description = "chanel for alarm manager"
            val importence = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("ToDoList", name, importence)
            channel.description = description
            val notificationManager = activity?.getSystemService(NotificationManager::class.java)

            notificationManager?.createNotificationChannel(channel)
        }
    }
}