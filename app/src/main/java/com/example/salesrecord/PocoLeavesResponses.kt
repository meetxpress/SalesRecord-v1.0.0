package com.example.salesrecord

import android.util.Log

class PocoLeavesResponses(var leave_id:String, var fromDate:String, var toDate:String, var type1:String, var type2:String, var reason:String, var status:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return  "\nDate(From): $fromDate \tType: $type1" +
                "\nDate(To): $toDate     \tType: $type2" +
                "\nReason: $reason \nStatus: $status"
    }
}