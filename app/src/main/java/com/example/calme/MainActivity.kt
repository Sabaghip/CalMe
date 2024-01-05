package com.example.calme

import android.os.Build
import android.os.Bundle
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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calme.MainActivity.Companion.categories
import com.example.calme.MainActivity.Companion.tasks
import com.example.calme.Model.Category
import com.example.calme.Model.Task
import com.example.calme.TasksWindow.TasksWindow
import com.example.calme.Utils.Tabs
import com.example.compose.AppTheme
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_tertiary
import com.example.compose.md_theme_light_tertiaryContainer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Date


val taskWindow = TasksWindow()
val customCalender = CustomCalender()
val customWeekly = CustomWeekly()

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                runBlocking{
                    initialize();
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = md_theme_light_background
                ) {
                    runApp()
                }
            }
        }
    }

    companion object {
        val tasks = ArrayList<Task>()
        val categories = ArrayList<Category>()
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
                navBar(selected =  Tabs.Tasks, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks= {tabState = Tabs.Tasks}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")})
                taskWindow.showTasks(MainActivity.tasks, onClickCreate = {navController.navigate("create")})
            }
        }
        composable("categories") {
            Column {
                navBar(selected =  Tabs.Categories, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCategories= {tabState = Tabs.Categories}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")})
                taskWindow.showCategories(categories= categories, onClickCreate = {navController.navigate("createCategory")}, navcontroller = navController)
            }
        }
        composable("upcomings") {
            Column {
                navBar(selected =  Tabs.Upcomings, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCategories= {tabState = Tabs.Categories; navController.navigate("categories")}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings})
                taskWindow.showUpcomings()
            }
        }
        composable("calender") {
            navBar(selected=Tabs.Calender, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender}, onClickWeekly =  {tabState = Tabs.Weekly;navController.navigate("weekly")}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")})
            customCalender.runCalender(navContrller = navController)
        }
        composable("weekly") {
            navBar(selected=Tabs.Weekly, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly =  {tabState = Tabs.Weekly}, onClickUpcomings = {tabState = Tabs.Upcomings;navController.navigate("upcomings")})
            customWeekly.runWeekly(navController = navController)
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
fun navBar(selected:Tabs, onClickTasks:() -> Unit, onClickCalender:() -> Unit, onClickWeekly:() -> Unit, onClickCategories:() -> Unit, onClickUpcomings:()->Unit){
    val buttons = ArrayList<temp>()
    buttons.add(temp(onClickTasks, Tabs.Tasks))
    buttons.add(temp(onClickCategories, Tabs.Categories))
    buttons.add(temp(onClickCalender, Tabs.Calender))
    buttons.add(temp(onClickWeekly, Tabs.Weekly))
    buttons.add(temp(onClickUpcomings, Tabs.Upcomings))
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

suspend fun initialize(){
    tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 10, 19, 18, 23)))
    tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 12, 20, 23)))
    tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 9, 19, 12, 23)))
    tasks.add(Task("Eating", "Eat lunch before take a nap", Date(2023, 11, 19, 18, 23)))
    tasks.add(Task("Watching Tv", "Watch football game after nap.", Date(2023, 11, 14, 20, 23)))
    tasks.add(Task("Play Video Game", "play new Video Game which my friend bought for me.", Date(2023, 11, 19, 12, 23)))

    val c1 = Category("c1 is something")
    val c2 = Category("c2 is something")
    categories.add(c1)
    categories.add(c2)
    c1.addTask(tasks.get(0))
    c1.addTask(tasks.get(1))
    c2.addTask(tasks.get(2))
    c2.addTask(tasks.get(1))
}

@Composable
fun test(){
    }

