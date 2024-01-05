package com.example.calme

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calme.MainActivity.Companion.categories
import com.example.calme.MainActivity.Companion.tasks
import com.example.calme.MainActivity.Companion.global_id
import com.example.calme.MainActivity.Companion.settings
import com.example.calme.MainActivity.Companion.editor
import com.example.calme.Model.Category
import com.example.calme.Model.CategoryForSave
import com.example.calme.Model.ListOfCategoriesForSave
import com.example.calme.Model.ListOfTasksForSave
import com.example.calme.Model.Task
import com.example.calme.Model.TaskForSave
import com.example.calme.TasksWindow.TasksWindow
import com.example.calme.Utils.Tabs
import com.example.compose.AppTheme
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_tertiary
import com.example.compose.md_theme_light_tertiaryContainer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import java.util.Date


val taskWindow = TasksWindow()
val customCalender = CustomCalender()
val customWeekly = CustomWeekly()

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = applicationContext.getSharedPreferences("callme", 0)
        editor = applicationContext.getSharedPreferences("callme", 0).edit()

        setContent {
            AppTheme {
                initialize();

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = md_theme_light_background
                ) {
                    runApp()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val taskssForSavee = ListOfTasksForSave(ArrayList<TaskForSave>())
        for(task in tasks){
            taskssForSavee.tasksForSave.add(
                TaskForSave(task.id,task.getTitle1(), task.getDescription1(), task.date.year, task.date.month,
                    task.date.date, task.date.hours, task.date.minutes, task.done))
        }

        val categoriesForSavee = ListOfCategoriesForSave(ArrayList<CategoryForSave>())
        for(category in categories){
            val temp = ArrayList<Int>()
            for(task in category.tasks){
                temp.add(task.id)
            }
            categoriesForSavee.categoriesForSave.add(CategoryForSave(category.title, temp))
        }

        editor?.putString("categories", Json.encodeToString(categoriesForSavee))

        editor?.putString("tasks", Json.encodeToString(taskssForSavee))
        editor?.putInt("id", global_id)
        editor?.apply()
    }

    companion object {
        val tasks = ArrayList<Task>()
        val categories = ArrayList<Category>()
        var settings : SharedPreferences? = null
        var editor = settings?.edit()
        var global_id = 0
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun runApp(){
    var tabState by remember { mutableStateOf(Tabs.Tasks) }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column {
                navBar(selected =  Tabs.Tasks, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks= {tabState = Tabs.Tasks}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")}, onClickStatistics = {tabState=Tabs.Statistics;navController.navigate("statistics")})
                taskWindow.showTasks(MainActivity.tasks, onClickCreate = {navController.navigate("create")})
            }
        }
        composable("categories") {
            Column {
                navBar(selected =  Tabs.Categories, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCategories= {tabState = Tabs.Categories}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")}, onClickStatistics = {tabState=Tabs.Statistics;navController.navigate("statistics")})
                taskWindow.showCategories(categories= categories, onClickCreate = {navController.navigate("createCategory")}, navcontroller = navController)
            }
        }
        composable("upcomings") {
            Column {
                navBar(selected =  Tabs.Upcomings, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCategories= {tabState = Tabs.Categories; navController.navigate("categories")}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings}, onClickStatistics = {tabState=Tabs.Statistics;navController.navigate("statistics")})
                taskWindow.showUpcomings()
            }
        }
        composable("calender") {
            navBar(selected=Tabs.Calender, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender}, onClickWeekly =  {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")}, onClickStatistics = {tabState=Tabs.Statistics;navController.navigate("statistics")})
            customCalender.runCalender(navContrller = navController)
        }
        composable("weekly") {
            navBar(selected=Tabs.Weekly, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly =  {tabState = Tabs.Weekly}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")}, onClickStatistics = {tabState=Tabs.Statistics;navController.navigate("statistics")})
            customWeekly.runWeekly(navController = navController)
        }
        composable("statistics") {
            Column {
                navBar(selected =  Tabs.Statistics, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks= {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")}, onClickStatistics = {tabState=Tabs.Statistics})
                taskWindow.showStatistics()
            }
        }
        composable("create") {
            Column {
                taskWindow.CreateTaskTab(onBackClicked = {navController.popBackStack()})
            }
        }
        composable("showTaskInCalender") {
            Column {
                taskWindow.ShowTasksInCalender(tasks = tasksToShow, onBackClicked = {navController.popBackStack()})
            }
        }
        composable("showTaskInWeekly") {
            Column {
                taskWindow.ShowTasksInCalender(tasks = tasksToShow, onBackClicked = {navController.popBackStack()})
            }
        }
        composable("showCategory") {
            Column {
                taskWindow.showCategory(onClickAdd={navController.navigate("addNewTaskToCategory")})
            }
        }
        composable("createCategory") {
            Column {
                taskWindow.createCategory(onBackClicked = {navController.popBackStack()})
            }
        }
        composable("addNewTaskToCategory") {
            Column {
                taskWindow.addNewTaskToCategory(onBackClicked={navController.popBackStack()})
            }
        }
    }
}
@Composable
fun navBar(selected:Tabs, onClickTasks:() -> Unit, onClickCalender:() -> Unit, onClickWeekly:() -> Unit, onClickCategories:() -> Unit, onClickUpcomings:()->Unit,onClickStatistics : () -> Unit){
    val buttons = ArrayList<temp>()
    buttons.add(temp(onClickTasks, Tabs.Tasks))
    buttons.add(temp(onClickCategories, Tabs.Categories))
    buttons.add(temp(onClickCalender, Tabs.Calender))
    buttons.add(temp(onClickWeekly, Tabs.Weekly))
    buttons.add(temp(onClickUpcomings, Tabs.Upcomings))
    buttons.add(temp(onClickStatistics, Tabs.Statistics))
    LazyRow() {
        items(buttons) { item ->
            ShowButton(button = item, selected=selected)
        }
    }
}

data class temp(
    val onClick:()->Unit,
    val selected:Tabs,
)

@Composable
fun ShowButton(button: temp, selected:Tabs) {
    Button(onClick = button.onClick, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==button.selected) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
        Text(text = button.selected.toString(), color = if(selected==button.selected) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
    }
}

fun initialize(){

    global_id = settings?.getInt("id", 0)!!
    val json = settings?.getString("tasks", "nullll")
    if (json != null) {
        if(json != "nullll"){
            val temp = json.let { Json.decodeFromString<ListOfTasksForSave>(json) } as ListOfTasksForSave

            for(task in temp.tasksForSave){
                tasks.add(Task(task.id,task.title, task.description, Date(task.year, task.month, task.day, task.hour,task.minute), task.done))
            }
        }
    }

    val jsonn = settings?.getString("categories", "nullll")
    if (jsonn != null) {
        if(jsonn != "nullll"){
            val temp = jsonn.let { Json.decodeFromString<ListOfCategoriesForSave>(jsonn) } as ListOfCategoriesForSave

            for(category in temp.categoriesForSave){
                val tempp = Category(category.title)
                categories.add(tempp)
                for(id in category.tasksId){
                    for(task in tasks){
                        if(task.id==id){
                            tempp.addTask(task)
                            break
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun test(){
    }

