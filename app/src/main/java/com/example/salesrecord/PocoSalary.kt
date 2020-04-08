package com.example.salesrecord

import android.util.Log

class PocoSalary (var emp_inc:String, var emp_totsal:String, var emp_month:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Month\tIncentive\tTotal Salary" +
                "\n$emp_month\t$emp_inc\t\t\t\t$emp_totsal"
    }
}