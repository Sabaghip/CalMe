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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calme.MainActivity.Companion.categories
import com.example.calme.Model.Category


val taskWindow = TasksWindow()
val customCalender = CustomCalender()
val customWeekly = CustomWeekly()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    companion object {
        val tasks = ArrayList<Task>()
        val categories = ArrayList<Category>()
    }
}



@Composable
fun runApp(){
    var tabState by remember { mutableStateOf(Tabs.Tasks) }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column {
                navBar(selected =  Tabs.Tasks, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks= {tabState = Tabs.Tasks}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")})
                taskWindow.showTasks(MainActivity.tasks, onClickCreate = {navController.navigate("create")})
            }
        }
        composable("categories") {
            Column {
                navBar(selected =  Tabs.Categories, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCategories= {tabState = Tabs.Categories}, onClickCalender={tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly = {tabState = Tabs.Weekly;navController.navigate("weekly")})
                taskWindow.showCategories(categories= categories, onClickCreate = {navController.navigate("createCategory")}, navcontroller = navController)
            }
        }
        composable("calender") {
            navBar(selected=Tabs.Calender, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender}, onClickWeekly =  {tabState = Tabs.Weekly;navController.navigate("weekly")})
            customCalender.runCalender(navContrller = navController)
        }
        composable("weekly") {
            navBar(selected=Tabs.Weekly, onClickCategories= {tabState = Tabs.Categories;navController.navigate("categories")}, onClickTasks =  {tabState = Tabs.Tasks; navController.navigate("home")}, onClickCalender =  {tabState = Tabs.Calender;navController.navigate("calender")}, onClickWeekly =  {tabState = Tabs.Weekly})
            customWeekly.runWeekly(navController = navController)
        }
        composable("create") {
            Column {
                taskWindow.CreateTaskTab(onBackClicked = {navController.navigate("home")})
            }
        }
        composable("showTaskInCalender") {
            Column {
                taskWindow.ShowTasksInCalender(tasks = tasksToShow, onBackClicked = {navController.navigate("calender")})
            }
        }
        composable("showTaskInWeekly") {
            Column {
                taskWindow.ShowTasksInCalender(tasks = tasksToShow, onBackClicked = {navController.navigate("weekly")})
            }
        }
        composable("showCategory") {
            Column {
                taskWindow.showCategory(onClickAdd={navController.navigate("addNewTaskToCategory")})
            }
        }
        composable("createCategory") {
            Column {
                taskWindow.createCategory(onBackClicked = {navController.navigate("categories")})
            }
        }
        composable("addNewTaskToCategory") {
            Column {
                taskWindow.addNewTaskToCategory(onBackClicked={navController.navigate("categories")})
            }
        }
    }
}
@Composable
fun navBar(selected:Tabs, onClickTasks:() -> Unit, onClickCalender:() -> Unit, onClickWeekly:() -> Unit, onClickCategories:() -> Unit){
    Row(modifier= Modifier){
        Button(onClick = onClickTasks, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Tasks) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Tasks", color = if(selected==Tabs.Tasks) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Button(onClick = onClickCategories, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Categories) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Categories", color = if(selected==Tabs.Categories) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickCalender, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Calender) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Calender", color = if(selected==Tabs.Calender) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(onClick = onClickWeekly, colors = ButtonDefaults.outlinedButtonColors(containerColor=(if(selected==Tabs.Weekly) md_theme_light_tertiaryContainer else md_theme_light_tertiary))) {
            Text(text = "Weekly", color = if(selected==Tabs.Weekly) md_theme_light_tertiary else md_theme_light_tertiaryContainer)
        }
    }
}

fun initialize(){
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

