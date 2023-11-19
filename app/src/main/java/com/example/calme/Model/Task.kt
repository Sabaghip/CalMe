package com.example.calme.Model

import java.util.Date

class Task(val title:String,val description:String,val date:Date) {

    public fun getTitle(): String {
        return this.title
    }
    public fun getDescription(): String {
        return this.description
    }
    public fun getDate(): Date{
        return this.date
    }
    public fun isExpired(): Boolean{
        return this.date < Date()
    }
}
