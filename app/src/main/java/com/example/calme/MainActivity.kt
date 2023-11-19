package com.example.calme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calme.Model.Task
import com.example.calme.ui.theme.CalMeTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalMeTheme {
                val tasks = ArrayList<Task>()
                initialize(tasks);
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        navBar()
                        showTasks(tasks)
                    }

                }
            }
        }
    }
}

@Composable
fun navBar(){
    Row(modifier= Modifier){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Tasks")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "ss1")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "ss2")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = { /*TODO*/ }) {
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
fun showTasks(array : ArrayList<Task>) {
    LazyColumn(modifier= Modifier.padding(top=10.dp)){
        items(array){item -> showTask(task = item)}
    }
}
@Composable
fun showTask(task:Task) {
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(20.dp))

    ) {
        Box(
            modifier = Modifier.background(Color.Green)

        ) {
            Column {
                Row() {
                    Text(
                        text = task.getTitle1(),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .height(40.dp),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = task.getDate1().toString(),
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .height(60.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = task.getDescription1(),
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                    fontSize = 13.sp,
                )


            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
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