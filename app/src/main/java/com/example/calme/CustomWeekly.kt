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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.calme.MainActivity.Companion.tasks
import com.example.calme.Model.DayOfWeekDTO
import com.example.calme.Model.Task
import java.time.Year
import java.util.Calendar
import java.util.Date
import kotlin.system.exitProcess

class CustomWeekly {
    @Composable
    fun runWeekly(navController:NavController){
        Weekly(Date(), navController)
    }
    @Composable
    fun Weekly(date : Date, navController: NavController) {
        val calendar = Calendar.getInstance();
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        while (dayOfWeek > 0) {
            if (dayOfMonth > 1) {
                dayOfMonth -= 1
            } else {
                if (month > 0) {
                    month -= 1
                    dayOfMonth = daysInMonth
                } else {
                    year -= 1
                    month = 11
                    dayOfMonth = daysInMonth
                }
            }
            dayOfWeek -= 1
        }
        Column(modifier = Modifier.padding(start = 10.dp, top = 200.dp)) {
            val array = ArrayList<DayOfWeekDTO>()
            array.add(DayOfWeekDTO(0, dayOfMonth, month, year))
            for (j in 1..6) {
                Spacer(modifier = Modifier.width(2.dp))
                if (dayOfMonth < daysInMonth) {
                    dayOfMonth += 1
                } else {
                    if (month < 11) {
                        month += 1
                        dayOfMonth = 1
                    } else {
                        year += 1
                        month = 0
                        dayOfMonth = 1
                    }
                }
                array.add(DayOfWeekDTO(j, dayOfMonth, month, year))
            }
            LazyRow(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .height(550.dp)
            ) {
                items(array) { item ->
                    showDayOnWeekly(
                        dayOfWeek = item.dayOfWeek,
                        day = item.day,
                        month = item.month,
                        year = item.year,
                        navController = navController
                    )
                }
            }

        }

    }

    fun showDay(){

    }
    @Composable
    fun showDayOnWeekly(navController: NavController ,dayOfWeek:Int, day:Int, month:Int,year:Int){
        val flag = dayHaveTask(day=day, month=month, year=year)
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(160.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(
                    if (flag) {
                        Color(0x777777FF)
                    } else {
                        Color(0x77777777)
                    }
                )
                .clickable {
                    if (flag) {
                        tasksToShow = getTasksOfDay(
                            day = day,
                            month = month,
                            year = year,
                            tasks = tasks
                        )
                        navController.navigate("showTaskInWeekly")
                    }
                },
        ){
            Text(
                text = "${getDayOfWeekName(dayOfWeek).toString()}\n${day}/${month}/${year}",
                modifier = Modifier.padding(
                    start = 10.dp,
                    end = 0.dp,
                    top = 20.dp,
                    bottom = 0.dp
                )
            )

        }
        Spacer(modifier = Modifier.width(25.dp))
    }

    fun dayHaveTask(day:Int, month:Int,year:Int):Boolean{
        for(task in tasks){
            if(task.getDate1().date == day && task.getDate1().month == month && task.getDate1().year == year){
                return true
            }
        }
        return false
    }
    fun getDayOfWeekName(month:Int):String{
        when(month){
            0 -> return "Saturday"
            1 -> return "Sunday"
            2 -> return "Monday"
            3 -> return "Tuesday"
            4 -> return "Wednesday"
            5 -> return "Thursday"
            6 -> return "Friday"
            else->return "Saturday"
        }
    }

    fun getTasksOfDay(day:Int, month: Int, year: Int, tasks:ArrayList<Task>):ArrayList<Task>{
        val res = ArrayList<Task>()
        for(task in tasks){
            if(task.getDate1().date == day && task.getDate1().month == month && task.getDate1().year == year){
                res.add(task)
            }
        }
        return res
    }
}

