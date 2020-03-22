package com.example.salesrecord

import android.util.Log

class PocoEmpLeaveReport(var lmy:String, var lCount:String, var AlCount:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Month \t\t Applied Leave \t\t Approved Leave" +
                "\n$lmy\t\t\t\t\t\t\t$lCount\t\t\t\t\t\t\t\t\t\t\t\t$AlCount"
    }
}