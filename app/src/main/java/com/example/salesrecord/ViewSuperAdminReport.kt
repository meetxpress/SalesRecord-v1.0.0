package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_view_super_admin_report.*

class ViewSuperAdminReport : AppCompatActivity() {
    var ss:String=" "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_super_admin_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        var reportArr = arrayOf("Company Report")
        val adap = ArrayAdapter(this@ViewSuperAdminReport, android.R.layout.simple_list_item_1, reportArr)

        displaySuperAdminRecord.adapter = adap
        adap.notifyDataSetChanged()

        displaySuperAdminRecord.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            ss = displaySuperAdminRecord.getItemAtPosition(position).toString()
            //Toast.makeText(this@ViewEmpReports, ss, Toast.LENGTH_SHORT).show()
            if(ss == "Company Report"){
                var i= Intent(this@ViewSuperAdminReport, SAdminCompanyReport::class.java)
                startActivity(i)
            }
        }
    }
}
