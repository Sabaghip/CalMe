package com.example.calme.Model

import android.os.Build
import android.text.format.Time
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.Date

class Task(val title:String,val description:String,val date:Date) {
    var done = false
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

class DayOfWeekDTO(val dayOfWeek:Int,val day:Int,val month:Int, val year:Int) {

}

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
