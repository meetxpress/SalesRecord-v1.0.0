package com.example.salesrecord

import android.util.Log

class PocoCompSalesReport (var totSales:String, var sal_month:String){
    override fun toString(): String {
        Log.d("lF","I Leave File")
        return " Month\t\t\tTotal Sale" +
                "\n$sal_month\t\t\t\t$totSales"
    }
}
