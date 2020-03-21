package com.example.salesrecord

import android.util.Log

class PocoLeaves(var emp_id:String, var leave_id:String, var fromDate:String, var toDate:String, var type1:String, var type2:String, var reason:String, var status:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Employee Id: $emp_id" +
                "\nDate(From): $fromDate \tType: $type1" +
                "\nDate(To): $toDate     \tType: $type2" +
                "\nReason: $reason"
    }
}