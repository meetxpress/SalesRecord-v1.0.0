package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
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

        //leaveDate.setText(SimpleDateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
        leaveDate.setText("03-2020")
        Log.v("id", emp_id)

        leaveDate.setOnClickListener{
            arrUser.clear()
        }

        btnGenerateLeave.setOnClickListener {
            var yr=leaveDate.text.toString()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if(leaveDate.text.toString() == " "){
                Toast.makeText(this@EmpLeaveReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()

            } else{
                Log.v("yr",leaveDate.text.toString())
                arrUser.clear()
                callService(emp_id, yr)
                adap = ArrayAdapter<PocoEmpLeaveReport>(this@EmpLeaveReport, android.R.layout.simple_list_item_1, arrUser)
                dispLeaveReport.adapter = adap
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
                .url("http://192.168.43.70/SalesRecord/callEmpLeaveReportService.php")
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

                            userobj = PocoEmpLeaveReport(yr, lcount, AlCount)
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
