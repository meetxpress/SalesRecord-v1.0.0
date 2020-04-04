package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comp_salary_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompSalaryReport : AppCompatActivity() {

    var arrUser = ArrayList<PocoCompSalaryReport>()
    var userobj:PocoCompSalaryReport = PocoCompSalaryReport("", "", "","")

    lateinit var adap: ArrayAdapter<PocoCompSalaryReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_salary_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        empSalMonth.setText(SimpleDateFormat("M-YYYY").format(Calendar.getInstance().time)).toString()



        btnGenCompSalary.setOnClickListener {
            if(empSalMonth.text.toString() == " "){
                Toast.makeText(this@CompSalaryReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()
            }else{
                callService(comp_id, empSalMonth.text.toString())
                adap = ArrayAdapter<PocoCompSalaryReport>(this@CompSalaryReport, android.R.layout.simple_list_item_1, arrUser)
                displayCompSalaryReport.adapter = adap
                adap.notifyDataSetChanged()
            }
        }
    }
    fun callService(comp_id:String, yr:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("comp_id",comp_id)
                .add("yr", yr)
                .build()
            Log.v("id",comp_id)
            Log.v("yr",yr)



            var req= Request.Builder()
                .url("http://192.168.49.231/SalesRecord/callEmpSalaryReportService.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        Log.v("res",str)
                        var js= JSONObject(str)
                        var flag=js.getInt("success")
                        var arr= js.getJSONArray("Salary")

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var eid=ua.getString("emp_id")
                            var emp_inc = ua.getString("emp_inc")
                            var emp_totsal = ua.getString("emp_totsal")
                            var emp_month = ua.getString("emp_month")

                            userobj = PocoCompSalaryReport(eid, emp_inc, emp_totsal, emp_month)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@CompSalaryReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@CompSalaryReport, "No Data Found.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

}
