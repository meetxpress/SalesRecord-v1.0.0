package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_leave_response.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ViewLeaveResponse : AppCompatActivity() {

    var arrUser = ArrayList<PocoLeavesResponses>()
    var userobj:PocoLeavesResponses = PocoLeavesResponses("","","","","","")

    var emp_id:String = " "
    lateinit var adap:ArrayAdapter<PocoLeavesResponses>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_leave_response)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        emp_id = preference.getString("uname","Wrong").toString()

        btnRefreshLeaveRes.setOnClickListener{
            Toast.makeText(this, emp_id, Toast.LENGTH_SHORT).show()
            callService(emp_id)
            adap = ArrayAdapter<PocoLeavesResponses>(this@ViewLeaveResponse, android.R.layout.simple_list_item_1, arrUser)
            dispLeaveRes.adapter = adap
            adap.notifyDataSetChanged()
        }
    }

    fun callService(emp_id:String) {
        try{
            val client = OkHttpClient()

            val formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .build()

            val request = Request.Builder()
                .url("http://192.168.43.231/SalesRecord/view_leaves_responses.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var data = response.body!!.string()
                        Log.v("str", data)

                        var obj = JSONObject(data)
                        var flag= obj.getInt("success")
                        var msg= obj.getString("message")
                        var uarr= obj.getJSONArray("LeaveRes")

                        Log.v("flag",flag.toString())
                        Log.v("msg",msg.toString())

                        for(i in 0 until uarr.length()) {
                            Log.v("loop", "In loop")
                            var ua = uarr.getJSONObject(i)

                            var fromDate = ua.getString("fromDate")
                            var toDate = ua.getString("toDate")
                            var type1 = ua.getString("type1")
                            var type2 = ua.getString("type2")
                            var reason = ua.getString("reason")
                            var status = ua.getString("status")

                            Log.v("fromDate",fromDate)
                            Log.v("toDate",toDate)
                            Log.v("type1",type1 )
                            Log.v("type2",type2 )
                            Log.v("reason",reason)
                            Log.v("status",status)

                            userobj = PocoLeavesResponses(fromDate, toDate, type1, type2, reason, status)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
        }catch (e: Exception) {
            Log.d("Exception", e.toString())
        }
    }
}
