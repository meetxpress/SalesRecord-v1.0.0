package com.example.salesrecord

import android.util.Log

class PocoCompSalaryReport(var eid:String, var emp_inc:Int, var emp_totsal:Int, var emp_month:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Emp_Id\t Month\t\t\tIncentive\tTotal Salary" +
                "\n$eid\t$emp_month\t\t\t\t $emp_inc\t\t\t\t\t\t$emp_totsal"
    }
}