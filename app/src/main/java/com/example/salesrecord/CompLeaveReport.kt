package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comp_leave_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompLeaveReport : AppCompatActivity() {
    var arrUser = ArrayList<PocoCompLeaveReport>()
    var userobj:PocoCompLeaveReport = PocoCompLeaveReport("", "", "")

    lateinit var adap: ArrayAdapter<PocoCompLeaveReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_leave_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        empLeaveMonth.setText(SimpleDateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
        //empLeaveMonth.setText("03-2020")
        empLeaveMonth.setOnClickListener{
            arrUser.clear()
        }

        btnGenCompLeave.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if(empLeaveMonth.text.toString() == " "){
                Toast.makeText(this@CompLeaveReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()
            }else{
                arrUser.clear()
                callService(comp_id, empLeaveMonth.text.toString())
                adap = ArrayAdapter<PocoCompLeaveReport>(this@CompLeaveReport, android.R.layout.simple_list_item_1, arrUser)
                displayCompLeaveReport.adapter = adap
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
                .url("http://192.168.43.70/SalesRecord/callCompLeaveService.php")
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
                        Log.v("al", js.getJSONArray("Leaves").length().toString())

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var eid=ua.getString("emp_id")
                            var l_count = ua.getString("l_count")
                            var a_count = ua.getString("a_count")

                            userobj = PocoCompLeaveReport(eid, l_count, a_count)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@CompLeaveReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@CompLeaveReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
