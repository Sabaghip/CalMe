package com.example.calme.Model

import java.util.Date

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
    public fun isExpired(): Boolean{
        return this.date < Date()
    }
}
