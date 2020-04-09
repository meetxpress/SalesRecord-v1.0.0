package com.example.salesrecord

import android.util.Log

class PocoSupAdminCompanyReport (var comp_id:String, var comp_name:String, var comp_city:String, var comp_mob1:String, var comp_person:String, var comp_status:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return "Company ID:\t$comp_id" +
                "\nName:\t$comp_name" +
                "\nCity:\t$comp_city " +
                "\nMobile. $comp_mob1" +
                "\nStatus:\t$comp_status"
    }
}