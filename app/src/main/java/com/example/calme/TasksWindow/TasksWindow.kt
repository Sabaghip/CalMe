package com.example.calme.TasksWindow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.calme.Model.Task
import com.example.calme.initialize
import com.example.calme.navBar
import com.example.calme.ui.theme.CalMeTheme
import java.util.Calendar
import java.util.Date
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.window.DialogProperties
import com.example.calme.MainActivity
import com.example.calme.taskWindow
import java.io.FileDescriptor
import java.sql.Time

class TasksWindow {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showTasks(array : ArrayList<Task>) {
        val craeteState = remember { mutableStateOf(false) }
        if(craeteState.value){
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color(0xDDFF0000)
            ){
                Box(modifier = Modifier.padding(top=50.dp, start = 50.dp)) {
                    Box(
                        modifier = Modifier
                            .height(1000.dp)
                            .width(240.dp)
                            .background(Color(0xDDFF0000))
                            .padding(start = 10.dp, top = 10.dp)
                    ) {
                        Column {

                            var title by remember { mutableStateOf(TextFieldValue("")) }
                            var description by remember { mutableStateOf(TextFieldValue("")) }
                            TextField(
                                value = title,
                                onValueChange = { title = it },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                label = { Text("Title") },
                                placeholder = { Text("title") },
                            )
                            TextField(
                                value = description,
                                onValueChange = { description = it },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp),
                                label = { Text("Description") },
                                placeholder = { Text("description") },
                            )
                            val mContext = LocalContext.current
                            val mYear: Int
                            val mMonth: Int
                            val mDay: Int

                            // Initializing a Calendar
                            val mCalendar = Calendar.getInstance()

                            // Fetching current year, month and day
                            mYear = mCalendar.get(Calendar.YEAR)
                            mMonth = mCalendar.get(Calendar.MONTH)
                            mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

                            mCalendar.time = Date()

                            // Declaring a string value to
                            // store date in string format
                            val mDate = remember { mutableStateOf(Date()) }

                            // Declaring DatePickerDialog and setting
                            // initial values as current values (present year, month and day)
                            val mDatePickerDialog = DatePickerDialog(
                                mContext,
                                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                    mDate.value = Date(mYear, mMonth, mDayOfMonth)
                                }, mYear, mMonth, mDay
                            )
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                                // Creating a button that on
                                // click displays/shows the DatePickerDialog
                                Button(onClick = {
                                    mDatePickerDialog.show()
                                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58)) ) {
                                    Text(text = "Select Date", color = Color.White)
                                }

                                // Adding a space of 100dp height
                                Spacer(modifier = Modifier.size(5.dp))

                                // Displaying the mDate value in the Text
                                Text(text = "Selected Date: ${mDate.value}", fontSize = 14.sp, textAlign = TextAlign.Center)


                                val mContext = LocalContext.current

                                // Declaring and initializing a calendar
                                val mCalendar = Calendar.getInstance()
                                val mHour = mCalendar[Calendar.HOUR_OF_DAY]
                                val mMinute = mCalendar[Calendar.MINUTE]

                                // Value for storing time as a string
                                val mTime = remember { mutableStateOf("") }

                                // Creating a TimePicker dialod
                                val mTimePickerDialog = TimePickerDialog(
                                    mContext,
                                    {_, mHour : Int, mMinute: Int ->
                                        mDate.value=Date(mDate.value.year, mDate.value.month, mDate.value.day, mHour, mMinute)
                                    }, mHour, mMinute, false
                                )

                                Column(modifier = Modifier.height(150.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                                    // On button click, TimePicker is
                                    // displayed, user can select a time
                                    Button(onClick = { mTimePickerDialog.show() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))) {
                                        Text(text = "Select Time", color = Color.White)
                                    }

                                    // Add a spacer of 100dp
                                    Spacer(modifier = Modifier.size(10.dp))

                                    // Display selected time
                                    Text(text = "Selected Time: ${mTime.value}", fontSize = 14.sp)
                                }
                                Row(modifier = Modifier.fillMaxSize().padding(start=20.dp)) {

                                    Button(onClick = { craeteState.value=false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))) {
                                        Text(text = "Back", color = Color.White)
                                    }

                                    Spacer(modifier = Modifier.size(10.dp))

                                    Button(enabled = title.text != "" && description.text != "" ,onClick = { craeteState.value=false;createTask(title=title.text, description=description.text, date=mDate.value) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))) {
                                        Text(text = "Add", color = Color.White)
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        else {
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(550.dp)
                ) {
                    items(array) { item -> ShowTask(task = item) }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
                    onClick = {
                        craeteState.value = !craeteState.value
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }
        }
    }

    fun createTask(title:String, description:String, date:Date){
        val newTask = Task(title=title, description=description, date=date)
        MainActivity.tasks.add(newTask)
    }

    @Composable
    fun ShowTask(task: Task) {
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