package com.example.salesrecord

import android.util.Log

class PocoCompTargetReport(var mon:String, var atarget:String, var p_target:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return " Month\t\tAssigned Target\t\tAchieved Target" +
                "\n$mon\t\t\t\t$atarget\t\t\t\t\t\t\t\t\t\t$p_target"
    }
}
