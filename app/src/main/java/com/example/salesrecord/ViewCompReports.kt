package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_view_comp_reports.*

class ViewCompReports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comp_reports)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        var reportArr = arrayOf("Target Report", " Sales Report", "Salary Report", "Leave Report", "Attendance Report")
        val adap = ArrayAdapter(this@ViewCompReports, android.R.layout.simple_list_item_1, reportArr)

        displayCompRecord.adapter = adap
        adap.notifyDataSetChanged()

        displayCompRecord.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            ss = displayCompRecord.getItemAtPosition(position).toString()
            //Toast.makeText(this@ViewEmpReports, ss, Toast.LENGTH_SHORT).show()
            if(ss == "Target Report") {
                var i=Intent(this@ViewCompReports, CompTargetReport::class.java)
                startActivity(i)
            } else if(ss == "Sales Report") {
                var i=Intent(this@ViewCompReports, CompSalesReport::class.java)
                startActivity(i)
            } else if(ss == "Salary Report"){
                var i=Intent(this@ViewCompReports, CompSalaryReport::class.java)
                startActivity(i)
            } else if(ss == "Leave Report"){
                var i= Intent(this@ViewCompReports, CompLeaveReport::class.java)
                startActivity(i)
            } else {
                var i=Intent(this@ViewCompReports, CompAttendanceReport::class.java)
                startActivity(i)
            }
        }
    }
}
