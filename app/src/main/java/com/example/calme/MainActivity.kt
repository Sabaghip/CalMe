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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
val customCalender = CustomCalender()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                initialize();
                var tabState by remember { mutableStateOf(Tabs.Tasks) }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = md_theme_light_background
                ) {
                    Column {
                        navBar(tabState, {tabState = Tabs.Tasks}, {tabState = Tabs.Calender}, {tabState = Tabs.Ss2}, {tabState = Tabs.Ss3})
                        when(tabState){
                            Tabs.Tasks -> taskWindow.showTasks(MainActivity.tasks)
                            Tabs.Calender -> customCalender.runCalender()
                            else -> test()
                        }

                    }
                }
            }
        }
    }

    companion object {
        val tasks = ArrayList<Task>()
    }
}



@Composable
fun navBar(selected:Tabs, onClickTasks:() -> Unit, onClickCalender:() -> Unit, onClickss2:() -> Unit, onClickss3:() -> Unit){
    Row(modifier= Modifier){
        Button(onClick = onClickTasks, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Tasks) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Tasks", color = if(selected==Tabs.Tasks) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickCalender, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Calender) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Calender", color = if(selected==Tabs.Calender) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
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
    MainActivity.tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 10, 19, 18, 23)))
    MainActivity.tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 12, 20, 23)))
    MainActivity.tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 9, 19, 12, 23)))
    MainActivity.tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 11, 19, 18, 23)))
    MainActivity.tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 14, 20, 23)))
    MainActivity.tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 11, 19, 12, 23)))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test(){
    }

