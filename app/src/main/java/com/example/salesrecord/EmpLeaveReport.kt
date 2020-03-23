package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_emp_leave_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmpLeaveReport : AppCompatActivity() {
    var arrUser = ArrayList<PocoEmpLeaveReport>()
    var userobj:PocoEmpLeaveReport = PocoEmpLeaveReport("", "", "")

    lateinit var adap: ArrayAdapter<PocoEmpLeaveReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_leave_report)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        leaveDate.setText(SimpleDateFormat("M-YYYY").format(Calendar.getInstance().time)).toString()
        Log.v("id",emp_id)

        btnGenerateLeave.setOnClickListener {
            var lmy=leaveDate.text.toString()
            if(leaveDate.text.toString() == " "){
                Toast.makeText(this@EmpLeaveReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()

            } else{
                Log.v("yr",leaveDate.text.toString())
                arrUser.clear()
                callService(emp_id, lmy)
                adap = ArrayAdapter<PocoEmpLeaveReport>(this@EmpLeaveReport, android.R.layout.simple_list_item_1, arrUser)
                dispLeaveReport.adapter = adap
                adap.notifyDataSetChanged()
            }
        }
    }

    fun callService(emp_id:String, lmy:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .add("lmy",lmy)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/callEmpLeaveReportService.php")
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
                        var arr= js.getJSONArray("Leaves")

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var lcount = ua.getString("l_count")
                            var AlCount = ua.getString("a_count")

                            userobj = PocoEmpLeaveReport(lmy, lcount, AlCount)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@EmpLeaveReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@EmpLeaveReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
