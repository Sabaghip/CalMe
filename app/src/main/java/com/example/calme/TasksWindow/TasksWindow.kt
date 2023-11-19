package com.example.calme.TasksWindow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calme.Model.Task

class TasksWindow {
    @Composable
    fun showTasks(array : ArrayList<Task>) {
        LazyColumn(modifier= Modifier.padding(top=10.dp)){
            items(array){item -> showTask(task = item)}
        }
    }
    @Composable
    fun showTask(task: Task) {
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
}