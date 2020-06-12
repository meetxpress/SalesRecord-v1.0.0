package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_emp_attendance_report.*
import kotlinx.android.synthetic.main.activity_emp_leave_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmpAttendanceReport : AppCompatActivity() {
    
    var arrUser = ArrayList<PocoEmpAttendanceReport>()
    var userobj:PocoEmpAttendanceReport = PocoEmpAttendanceReport("", "", "")

    lateinit var adap: ArrayAdapter<PocoEmpAttendanceReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_attendance_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        AttMonth.setText(SimpleDateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
        Log.v("id", emp_id)

        AttMonth.setOnClickListener{
            arrUser.clear()
        }

        btnGenerateAttendance.setOnClickListener {
            var yr=AttMonth.text.toString()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if(AttMonth.text.toString() == " "){
                Toast.makeText(this@EmpAttendanceReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()

            } else{
                Log.v("yr",AttMonth.text.toString())
                arrUser.clear()
                callService(emp_id, yr)
                adap = ArrayAdapter<PocoEmpAttendanceReport>(this@EmpAttendanceReport, android.R.layout.simple_list_item_1, arrUser)
                dispAttendanceReport.adapter = adap
                adap.notifyDataSetChanged()
            }
        }
    }

    fun callService(emp_id:String, yr:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .add("yr", yr)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/callEmpAttendanceReportService.php")
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
                        var arr= js.getJSONArray("Attendance")

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var tot_days = ua.getString("tot_days")
                            var tot_att = ua.getString("tot_att")

                            userobj = PocoEmpAttendanceReport(yr, tot_days, tot_att)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@EmpAttendanceReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@EmpAttendanceReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
