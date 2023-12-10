package com.example.calme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calme.MainActivity.Companion.tasks
import com.example.calme.Model.Task
import com.example.calme.TasksWindow.TasksWindow
import com.example.calme.Utils.Tabs
import com.example.compose.AppTheme
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_tertiary
import com.example.compose.md_theme_light_tertiaryContainer
import java.util.Calendar
import java.util.Date


val taskWindow = TasksWindow()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                initialize();
                Calender(Date())

                //var tabState by remember { mutableStateOf(Tabs.Tasks) }
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = md_theme_light_background
//                ) {
//                    Column {
//                        navBar(tabState, {tabState = Tabs.Tasks}, {tabState = Tabs.Ss1}, {tabState = Tabs.Ss2}, {tabState = Tabs.Ss3})
//                        when(tabState){
//                            Tabs.Tasks -> taskWindow.showTasks(MainActivity.tasks)
//                            Tabs.Ss1 -> test()
//                            else -> test()
//                        }
//
//                    }
//                }
            }
        }
    }

    companion object {
        val tasks = ArrayList<Task>()
    }
}

@Composable
fun Calender(date : Date){
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
    val tasksOfMonth = taskWindow.getTasksOfMonth(tasks, month, year)
    val daysWithTask = daysHaveTask(tasks)
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

                                    },
                            ) {
                                Text(
                                    text = "$z",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 0.dp,
                                        top = 8.dp,
                                        bottom = 0.dp
                                    )
                                )
                                z += 1
                            }
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
        res.add(task.getDate1().day)
    }
    return res
}
fun getTaskOfDay(day:Int, month:Int, year:Int, tasks:ArrayList<Task>){

}
fun getTaskOfDay(tasks : ArrayList<Task>):ArrayList<Int>{
    val res = ArrayList<Int>()
    for(task in tasks){
        res.add(task.getDate1().day)
    }
    return res
}
@Composable
fun navBar(selected:Tabs, onClickTasks:() -> Unit, onClickss1:() -> Unit, onClickss2:() -> Unit, onClickss3:() -> Unit){
    Row(modifier= Modifier){
        Button(onClick = onClickTasks, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Tasks) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Tasks", color = if(selected==Tabs.Tasks) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickss1, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Ss1) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "ss1", color = if(selected==Tabs.Ss1) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickss2, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Ss2) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "ss2", color = if(selected==Tabs.Ss2) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickss3, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Ss3) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "ss3", color = if(selected==Tabs.Ss3) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
    }
}

fun initialize(){
    MainActivity.tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 11, 19, 18, 23)))
    MainActivity.tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 19, 20, 23)))
    MainActivity.tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 11, 19, 12, 23)))
    MainActivity.tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 11, 19, 18, 23)))
    MainActivity.tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 19, 20, 23)))
    MainActivity.tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 11, 19, 12, 23)))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test(){
    }