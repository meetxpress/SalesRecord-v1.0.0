package com.example.salesrecord

import android.util.Log

class PocoEmpAttendanceReport (var yr:String, var tot_days:String, var tot_att:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Month \t\t Total Days \t\t Attendance" +
                "\n$yr\t\t\t\t$tot_days\t\t\t\t\t\t\t\t\t\t$tot_att"
    }
}