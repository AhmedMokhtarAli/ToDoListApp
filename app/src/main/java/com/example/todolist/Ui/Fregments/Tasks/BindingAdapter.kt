package com.example.todolist.Ui.Fregments.Tasks

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.todolist.R
import com.example.todolist.model.Task

@BindingAdapter("setHour","setMinte")
fun TextView.setTime(hour: Int,minte: Int){
    this.setText(setTimeFormatte(hour,minte))
}
@BindingAdapter("setImageResource")
fun ImageView.setImageResource(praiorty:Int){
    when(praiorty){
        3 -> this.setImageResource(R.drawable.red_circle)
        2 -> this.setImageResource(R.drawable.circile_yellow)
        else -> this.setImageResource(R.drawable.green_circle)
    }
}

@BindingAdapter("navToUpdate")
fun View.OnClick(task: Task){
    this.setOnClickListener(View.OnClickListener { navigateToUpdateTask(task,this) })
}

private fun setTimeFormatte(hour: Int,minte: Int): String {
    var am_pm=""
    var taskHour=hour
    when{taskHour==0 -> { taskHour+=12
        am_pm="AM"
    }
        taskHour==12 -> am_pm="PM"
        taskHour>12 -> { taskHour-=12
            am_pm = "PM"
        }
        else -> am_pm="AM"
    }
    return StringBuilder().append(if (taskHour <10) "0" + taskHour else taskHour).append(" : ").append(if (minte <10) "0" + minte else minte)
        .append(" $am_pm").toString()
}
fun navigateToUpdateTask(task: Task,view:View){
    val action = TasksDirections.actionTasksToUpdate(task)
    view.findNavController().navigate(action)
}
