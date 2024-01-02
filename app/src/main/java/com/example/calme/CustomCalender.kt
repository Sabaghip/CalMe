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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calme.Model.Task
import java.util.Calendar
import java.util.Date

var tasksToShow = ArrayList<Task>()
class CustomCalender {
    @Composable
    fun runCalender(navContrller : NavController){
            Calender(Date(), navContrller = navContrller)
    }

    @Composable
    fun Calender(date : Date, navContrller:NavController){
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
        Column(modifier = Modifier.padding(start = 10.dp, top = 100.dp)) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${getMonthName(month)} ${year}", fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
            }

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
                            showDayOnCalender(z=z, daysWithTask = daysWithTask, tasksOfMonth = tasksOfMonth, navContrller=navContrller)
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
    fun getTasksOfDay(day:Int, tasks:ArrayList<Task>):ArrayList<Task>{
        val res = ArrayList<Task>()
        for(task in tasks){
            if(task.getDate1().date == day){
                res.add(task)
            }
        }
        return res
    }

    fun getMonthName(month:Int):String{
        when(month){
            0 -> return "January"
            1 -> return "February"
            2 -> return "March"
            3 -> return "April"
            4 -> return "May"
            5 -> return "June"
            6 -> return "July"
            7 -> return "August"
            8 -> return "September"
            9 -> return "October"
            10 -> return "November"
            11 -> return "December"
            else->return "January"
        }
    }

    @Composable
    fun showDayOnCalender(z:Int, daysWithTask:ArrayList<Int>, tasksOfMonth : ArrayList<Task>, navContrller:NavController){
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
                        tasksToShow = getTasksOfDay(
                            day = z,
                            tasks = tasksOfMonth
                        )
                        navContrller.navigate("showTaskInCalender")
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