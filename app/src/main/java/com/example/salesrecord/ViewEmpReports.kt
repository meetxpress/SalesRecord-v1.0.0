package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_view_emp_reports.*

class ViewEmpReports : AppCompatActivity() {

    var reportArr = arrayOf("Target Report", "Sales Report", "Salary Report", "Leave Report", "Attendance Report")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_emp_reports)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        //callService(comp_id)
        var adap = ArrayAdapter(this@ViewEmpReports, android.R.layout.simple_list_item_1, reportArr)
        displayEmpRecord.adapter = adap
        adap.notifyDataSetChanged()
    }
}
