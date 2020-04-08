package com.example.salesrecord

import android.util.Log
class PocoCompLeaveReport (var eid:String, var l_count:String, var a_count:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return " Emp_id\t\tTotal Leave\t\tApproved Leave" +
                "\n$eid\t\t\t\t\t$l_count\t\t\t\t\t\t\t\t\t\t\t\t$a_count"
    }
}
