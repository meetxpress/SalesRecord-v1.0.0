package com.example.salesrecord

class Leaves(var emp_id:String, var fromDate:String, var toDate:String, var type1:String, var type2:String, var reason:String, var status:String){
    override fun toString(): String {
        return "Employee Id: $emp_id\nDate(From): $fromDate\nDate(To): $toDate\nType(from Date): $type1\nType(to Date): $type2\nReason: $reason"
    }
}