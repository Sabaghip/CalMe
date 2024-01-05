package com.example.calme.Model

import android.os.Build
import android.text.format.Time
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Date
import kotlinx.serialization.Serializable


class Task(val id : Int, val title:String, val description:String, val date:Date, var done:Boolean) {
    public fun getTitle1(): String {
        return this.title
    }
    public fun getDescription1(): String {
        return this.description
    }
    public fun getDate1(): Date{
        return this.date
    }
    public fun makeDone(){
        this.done = true
    }
    public fun isDone1():Boolean{
        return this.done
    }
    @RequiresApi(Build.VERSION_CODES.O)
    public fun isExpired(): Boolean{
        if(done){
            return false
        }
        val now = LocalDate.now()
        if(this.date.year < now.year){
            return true
        }
        else if(this.date.year > now.year){
            return false
        }
        if(this.date.month + 1 < now.monthValue){
            return true
        }
        else if(this.date.month + 1 > now.monthValue){
            return false
        }
        if(this.date.date + 1 < now.dayOfMonth){
            return true
        }
        else if(this.date.date + 1 > now.dayOfMonth){
            return false
        }
        if(this.date.hours < Time.HOUR){
            return true
        }
        else if(this.date.hours > Time.HOUR){
            return false
        }
        if(this.date.minutes < Time.MINUTE){
            return true
        }
        else if(this.date.minutes > Time.MINUTE){
            return false
        }

        return true
    }
}

@Serializable
data class TaskForSave(val id:Int,val title:String,val description:String,val year:Int, val month:Int, val day:Int, val hour:Int, val minute:Int, val done:Boolean)

class DayOfWeekDTO(val dayOfWeek:Int,val day:Int,val month:Int, val year:Int) {

}
@Serializable
data class ListOfTasksForSave(val tasksForSave: ArrayList<TaskForSave>)

class Category(val title:String) {
    val tasks = ArrayList<Task>()

    public fun getTitle1(): String {
        return this.title
    }
    public fun getTasks1() : ArrayList<Task>{
        return this.tasks
    }
    public fun addTask(task:Task){
        for(taskk in tasks){
            if(taskk == task){
                return
            }
        }
        tasks.add(task)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun getStatistic(): String{
        var allTasks = 0
        var expiredTasks = 0
        var doneTasks = 0
        for(task in tasks){
            allTasks += 1
            if(task.isExpired()){
                expiredTasks += 1
            }
            if(task.isDone1()){
                doneTasks += 1
            }
        }
        return "All = " + allTasks.toString() + "\nDone = " + doneTasks.toString() + "\nExpired = " + expiredTasks.toString()
    }
}
@Serializable
data class CategoryForSave(val title:String, val tasksId : ArrayList<Int>)

@Serializable
data class ListOfCategoriesForSave(val categoriesForSave: ArrayList<CategoryForSave>)
