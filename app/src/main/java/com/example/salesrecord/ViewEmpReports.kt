package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_emp_reports.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

var ss:String=" "

class ViewEmpReports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_emp_reports)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        var reportArr = arrayOf("Salary Report", "Leave Report")
        val adap = ArrayAdapter(this@ViewEmpReports, android.R.layout.simple_list_item_1, reportArr)

        displayEmpRecord.adapter = adap
        adap.notifyDataSetChanged()

        displayEmpRecord.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            ss = displayEmpRecord.getItemAtPosition(position).toString()
            Toast.makeText(this@ViewEmpReports, ss, Toast.LENGTH_SHORT).show()
            if(ss == "Salary Report"){
                var i=Intent(this@ViewEmpReports, EmpSalaryReport::class.java)
                startActivity(i)
            }else if(ss == "Leave Report"){
                //callLeaveReportService(emp_id)
                var i=Intent(this@ViewEmpReports, ::class.java)
                startActivity(i)
            }
        }
    }
}
