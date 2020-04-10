package com.example.salesrecord

import android.util.Log

class PocoCompAttendanceReport(var eid:String, var tot_att:String, var tot_days:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Emp_id\t\tTotal Days\t\tAttendance" +
                "\n$eid\t\t\t\t\t$tot_days\t\t\t\t\t\t\t\t$tot_att"
    }
}