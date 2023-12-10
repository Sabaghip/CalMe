package com.example.calme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calme.Model.Task
import java.util.Calendar
import java.util.Date

var taskToShow = Task("tt", "dd", Date())
class CustomCalender {
    @Composable
    fun runCalender(){
        val shouldShowTask = remember { mutableStateOf(false) }
        if(!shouldShowTask.value) {
            Calender(Date(), onShowTaskClicked = { shouldShowTask.value = true })
        }
        else{
            taskWindow.ShowTaskInCalender(task = taskToShow, onBackClicked = {shouldShowTask.value = false})
        }
    }

    @Composable
    fun Calender(date : Date, onShowTaskClicked:()->Unit){
        val calendar = Calendar.getInstance();
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        var backwardForFirstDayInWeek = dayOfWeek - ((dayOfMonth - 1)%7)
        if (backwardForFirstDayInWeek < 0){
            backwardForFirstDayInWeek = backwardForFirstDayInWeek + 7
        }
        var flag = false
        var z = 1
        val tasksOfMonth = taskWindow.getTasksOfMonth(MainActivity.tasks, month, year)
        val daysWithTask = daysHaveTask(tasksOfMonth)
        Column {
            Row {
                Spacer(modifier = Modifier.width(9.dp))
                Text(text = "Sat")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Sun")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Mon")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Tue")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Wed")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Thu")
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "Fri")
            }
            for(i in 0..5){
                Row {
                    for(j in 0..6){
                        if(j==backwardForFirstDayInWeek){
                            flag = true
                        }
                        if(flag && z < daysInMonth) {
                            xxxx(onShowTaskClicked=onShowTaskClicked, z=z, daysWithTask = daysWithTask, tasksOfMonth = tasksOfMonth)
                            z += 1
                        }
                        else{
                            Box(
                                modifier = Modifier
                                    .height(46.dp)
                                    .width(46.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0x11111111))
                                    .clickable { /*TODO*/ },
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }

        }
    }

    fun daysHaveTask(tasks : ArrayList<Task>):ArrayList<Int>{
        val res = ArrayList<Int>()
        for(task in tasks){
            res.add(task.getDate1().date)
        }
        return res
    }
    fun getTaskOfDay(day:Int, tasks:ArrayList<Task>):Task{
        for(task in tasks){
            if(task.getDate1().date == day){
                return task
            }
        }
        return Task("title", "desc", Date())
    }

    fun getTaskOfDay(tasks : ArrayList<Task>):ArrayList<Int>{
        val res = ArrayList<Int>()
        for(task in tasks){
            res.add(task.getDate1().date)
        }
        return res
    }

    @Composable
    fun xxxx(z:Int, daysWithTask:ArrayList<Int>, tasksOfMonth : ArrayList<Task>, onShowTaskClicked:()->Unit){
        Box(
            modifier = Modifier
                .height(46.dp)
                .width(46.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (z in daysWithTask) {
                        Color(0x777777FF)
                    } else {
                        Color(0x77777777)
                    }
                )
                .clickable {
                    if (z in daysWithTask) {
                        taskToShow = getTaskOfDay(
                            day = z,
                            tasks = tasksOfMonth
                        )
                        onShowTaskClicked()
                    }
                },
        ){
            Text(
                text = "$z",
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 0.dp,
                    top = 8.dp,
                    bottom = 0.dp
                )
            )

        }
    }
}