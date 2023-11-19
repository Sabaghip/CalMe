package com.example.calme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calme.Model.Task
import com.example.calme.TasksWindow.TasksWindow
import com.example.calme.ui.theme.CalMeTheme

import androidx.compose.material3.ButtonDefaults
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalMeTheme {
                val tasks = ArrayList<Task>()
                initialize(tasks);
                val taskWindow = TasksWindow()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        navBar("tasks")
                        taskWindow.showTasks(tasks)
                    }
                }
            }
        }
    }
}

@Composable
fun navBar(selected:String){
    Row(modifier= Modifier){
        Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected=="tasks") Color.Red else Color.Yellow))) {
            Text(text = "Tasks")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected=="ss1") Color.Red else Color.Yellow))) {
            Text(text = "ss1")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected=="ss2") Color.Red else Color.Yellow))) {
            Text(text = "ss2")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected=="ss3") Color.Red else Color.Yellow))) {
            Text(text = "ss3")
        }
    }
}

fun initialize(array: ArrayList<Task>){
    array.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 11, 19, 18, 23)))
    array.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 19, 20, 23)))
    array.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 11, 19, 12, 23)))
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalMeTheme {
        Greeting("Android")
    }
}