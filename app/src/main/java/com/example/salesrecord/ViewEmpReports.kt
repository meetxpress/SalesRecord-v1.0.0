package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ViewEmpReports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_emp_reports)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }
}
