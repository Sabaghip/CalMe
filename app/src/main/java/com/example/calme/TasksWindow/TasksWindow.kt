package com.example.calme.TasksWindow

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.calme.Model.Task
import com.example.calme.initialize
import com.example.calme.navBar
import com.example.calme.ui.theme.CalMeTheme

class TasksWindow {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showTasks(array : ArrayList<Task>) {
        val popUpState = remember { mutableStateOf(false) }
        Box() {
            LazyColumn(modifier = Modifier
                .padding(top = 10.dp)
                .height(550.dp)) {
                items(array) { item -> showTask(task = item) }
            }
        }

        if(popUpState.value){
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties()
            ){
                Box(modifier = Modifier.padding(top=250.dp)) {
                    Box(
                        modifier = Modifier
                            .height(250.dp)
                            .width(240.dp)
                            .background(Color(0xDDFF0000))
                            .padding(start = 10.dp, top = 10.dp)
                    ) {
                        Column {
                            var title by remember { mutableStateOf(TextFieldValue("")) }
                            var description by remember { mutableStateOf(TextFieldValue("")) }
                            TextField(
                                value = title,
                                onValueChange = { newValue -> title = newValue },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                label = { Text("Title") },
                                placeholder = { Text("Title") },
                            )
                            TextField(
                                value = description,
                                onValueChange = { newValue -> description = newValue },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                label = { Text("Description") },
                                placeholder = { Text("Description") },
                            )
                        }

                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    popUpState.value = !popUpState.value
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
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