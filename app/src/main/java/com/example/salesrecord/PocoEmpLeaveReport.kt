package com.example.salesrecord

import android.util.Log

class PocoEmpLeaveReport(var lmy:String, var lCount:String, var AlCount:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Month\t\tApplied Leave\t\tApproved Leave" +
                "$lmy\t\tlCount\t\tAlCount"
    }
}