package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comp_attendance_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompAttendanceReport : AppCompatActivity() {
    var arrUser = ArrayList<PocoCompAttendanceReport>()
    var userobj:PocoCompAttendanceReport = PocoCompAttendanceReport("", "", "")

    lateinit var adap: ArrayAdapter<PocoCompAttendanceReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_attendance_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        empAttMonth.setText(SimpleDateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
        //empAttMonth.setText("03-2020")
        empAttMonth.setOnClickListener{
            arrUser.clear()
        }

        btnGenCompAttendance.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if (empAttMonth.text.toString() == " ") {
                Toast.makeText(this@CompAttendanceReport, "Required Fields are missing.", Toast.LENGTH_SHORT).show()
            } else {
                arrUser.clear()
                callService(comp_id, empAttMonth.text.toString())
                adap = ArrayAdapter<PocoCompAttendanceReport>(this@CompAttendanceReport, android.R.layout.simple_list_item_1, arrUser)
                displayCompAttendanceReport.adapter = adap
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
                .url("http://192.168.43.215/SalesRecord/callCompAttendanceService.php")
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
                        Log.v("al", js.getJSONArray("Attendance").length().toString())

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var eid=ua.getString("emp_id")
                            var tot_att = ua.getString("tot_att")
                            var tot_days = ua.getString("tot_days")

                            userobj = PocoCompAttendanceReport(eid, tot_att, tot_days)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@CompAttendanceReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@CompAttendanceReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
