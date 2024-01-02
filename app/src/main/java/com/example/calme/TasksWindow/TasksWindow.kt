package com.example.calme.TasksWindow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import com.example.calme.MainActivity
import com.example.calme.Model.Category
import com.example.calme.Model.Task
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_onPrimaryContainer
import com.example.compose.md_theme_light_primary
import com.example.compose.md_theme_light_primaryContainer
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import kotlin.system.exitProcess

var categoryToShow = Category("E")
class TasksWindow {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showTasks(array: ArrayList<Task>, onClickCreate: () -> Unit) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
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
                    onClickCreate()
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }

    fun createTask(title: String, description: String, date: Date) {
        val newTask = Task(title = title, description = description, date = date)
        MainActivity.tasks.add(newTask)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateTaskTab(onBackClicked: () -> Unit) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = md_theme_light_background
        ) {
            Box(modifier = Modifier.padding(top = 50.dp, start = 50.dp)) {
                Box(
                    modifier = Modifier
                        .height(1000.dp)
                        .width(240.dp)
                        .background(md_theme_light_background)
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
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Creating a button that on
                            // click displays/shows the DatePickerDialog
                            Button(
                                onClick = {
                                    mDatePickerDialog.show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                            ) {
                                Text(text = "Select Date", color = Color.White)
                            }

                            // Adding a space of 100dp height
                            Spacer(modifier = Modifier.size(5.dp))


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
                                { _, mHour: Int, mMinute: Int ->
                                    mDate.value = Date(
                                        mDate.value.year,
                                        mDate.value.month,
                                        mDate.value.date,
                                        mHour,
                                        mMinute
                                    )
                                }, mHour, mMinute, false
                            )
                            Text(
                                text = "Selected Date: ${mDate.value.date}/${mDate.value.month + 1}/${mDate.value.year}",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 25.dp)
                            )

                            Column(
                                modifier = Modifier.height(150.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                // On button click, TimePicker is
                                // displayed, user can select a time
                                Button(
                                    onClick = { mTimePickerDialog.show() },
                                    colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                                ) {
                                    Text(text = "Select Time", color = Color.White)
                                }

                                // Add a spacer of 100dp
                                Spacer(modifier = Modifier.size(10.dp))

                                // Display selected time
                                Text(
                                    text = "Selected Time: ${mDate.value.hours}:${mDate.value.minutes}",
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 25.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 30.dp)
                            ) {

                                Button(
                                    onClick = { onBackClicked() },
                                    colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                                ) {
                                    Text(text = "Back", color = Color.White)
                                }

                                Spacer(modifier = Modifier.size(10.dp))

                                Button(
                                    enabled = title.text != "" && description.text != "",
                                    onClick = {
                                        createTask(
                                            title = title.text,
                                            description = description.text,
                                            date = mDate.value
                                        );onBackClicked()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                                ) {
                                    Text(text = "Add", color = Color.White)
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowTasksInCalender(tasks: ArrayList<Task>, onBackClicked: () -> Unit) {
        Column {
            Row() {
                Button(onClick = onBackClicked) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Tasks of ${tasks[0].getDate1().date}/${tasks[0].getDate1().month}/${tasks[0].getDate1().year}",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(550.dp)
                ) {
                    items(tasks) { item -> ShowTask(task = item) }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowTask(task: Task) {
        var expanded by remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp))

        ) {
            Box(
                modifier = Modifier.background(if(task.isDone1()) Color(0xFF008000) else if (task.isExpired()) Color(0xFF800000) else md_theme_light_primaryContainer)

            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = task.getTitle1(),
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .height(40.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        var date =
                            task.getDate1().year.toString() + "-" + (task.getDate1().month + 1).toString() + "-" + task.getDate1().date.toString() + " " + task.getDate1().hours.toString() + ":" + task.getDate1().minutes.toString()
                        Text(
                            text = date,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .height(60.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "show description"
                            )
                        }
                    }
                    if (expanded) {
                        Column {
                            Text(
                                text = task.getDescription1(),
                                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                                color = md_theme_light_onPrimaryContainer,
                                fontSize = 13.sp,
                            )
                            Row{
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { task.makeDone() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "show description"
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }


                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

    @Composable
    fun getTasksOfMonth(tasks: ArrayList<Task>, month: Int, year: Int): ArrayList<Task> {
        val res = ArrayList<Task>()
        for (task in tasks) {
            if (task.getDate1().month == month && task.getDate1().year == year) {
                res.add(task)
            }
        }
        return res
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun showCategories(
        categories: ArrayList<Category>,
        onClickCreate: () -> Unit,
        navcontroller: NavController
    ) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                items(categories) { item ->
                    ShowCategory(
                        category = item,
                        navcontroller = navcontroller
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    onClickCreate()
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowCategory(category: Category, navcontroller: NavController) {
        var expanded by remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable { categoryToShow = category; navcontroller.navigate("showCategory") }
        ) {
            Box(
                modifier = Modifier.background(md_theme_light_primaryContainer)

            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = category.getTitle1(),
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .height(40.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "show statistics"
                            )
                        }
                    }
                    if (expanded) {
                        Text(
                            text = category.getStatistic(),
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontSize = 13.sp,
                        )
                    }


                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun showCategory(onClickAdd: () -> Unit) {
        val tasks = categoryToShow.getTasks1()
        Column {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = categoryToShow.getTitle1(), fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
            }
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(550.dp)
                ) {
                    items(tasks) { item -> ShowTask(task = item) }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    onClickAdd()
                }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun createCategory(onBackClicked: () -> Unit) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = md_theme_light_background
        ) {
            Box(modifier = Modifier.padding(top = 50.dp, start = 50.dp)) {
                Box(
                    modifier = Modifier
                        .height(1000.dp)
                        .width(240.dp)
                        .background(md_theme_light_background)
                        .padding(start = 10.dp, top = 10.dp)
                ) {
                    Column {
                        var title by remember { mutableStateOf(TextFieldValue("")) }
                        TextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier
                                .padding(8.dp)
                                .width(200.dp),
                            label = { Text("Title") },
                            placeholder = { Text("title") },
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 30.dp)
                        ) {

                            Button(
                                onClick = { onBackClicked() },
                                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                            ) {
                                Text(text = "Back", color = Color.White)
                            }

                            Spacer(modifier = Modifier.size(10.dp))

                            Button(
                                enabled = title.text != "",
                                onClick = { MainActivity.categories.add(Category(title = title.text));onBackClicked() },
                                colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primary)
                            ) {
                                Text(text = "Add", color = Color.White)
                            }
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun addNewTaskToCategory(onBackClicked: () -> Unit) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                items(MainActivity.tasks) { item -> ShowTaskForAddToCategory(task = item, onClickBack = onBackClicked) }
            }
        }
    }

    @Composable
    fun ShowTaskForAddToCategory(task: Task, onClickBack:()->Unit) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp))

        ) {
            Box(
                modifier = Modifier
                    .background(md_theme_light_primaryContainer)
                    .clickable { categoryToShow.addTask(task); onClickBack() }

            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = task.getTitle1(),
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .height(40.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        var date =
                            task.getDate1().year.toString() + "-" + (task.getDate1().month + 1).toString() + "-" + task.getDate1().date.toString() + " " + task.getDate1().hours.toString() + ":" + task.getDate1().minutes.toString()
                        Text(
                            text = date,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .height(60.dp),
                            color = md_theme_light_onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}