package com.example.calme.Model

import android.os.Build
import android.text.format.Time
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Date
import kotlin.system.exitProcess

class Task(val title:String,val description:String,val date:Date) {
    public fun getTitle1(): String {
        return this.title
    }
    public fun getDescription1(): String {
        return this.description
    }
    public fun getDate1(): Date{
        return this.date
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
        if(this.date.month < now.monthValue){
            return true
        }
        else if(this.date.month > now.monthValue){
            return false
        }
        if(this.date.date < now.dayOfMonth){
            return true
        }
        else if(this.date.date > now.dayOfMonth){
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
