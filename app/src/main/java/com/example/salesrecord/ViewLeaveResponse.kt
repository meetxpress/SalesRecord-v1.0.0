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
import java.lang.Exception

class ViewLeaveResponse : AppCompatActivity() {

    var arrUser2 = ArrayList<PocoLeavesResponses>()
    var userobj:PocoLeavesResponses = PocoLeavesResponses("", "", "","","","", "")

    lateinit var adap:ArrayAdapter<PocoLeavesResponses>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_leave_response)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        btnRefreshLeaveRes.setOnClickListener{
            runOnUiThread{
                callService(emp_id)
                adap = ArrayAdapter<PocoLeavesResponses>(this@ViewLeaveResponse, android.R.layout.simple_list_item_1, arrUser2)
                displayLeaveRes.adapter = adap
                adap.notifyDataSetChanged()
            }
        }
    }

    fun callService(emp_id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/view_leave_responses.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ex", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        var str=response.body!!.string()
                        Log.v("str",str)

                        var js = JSONObject(str)
                        var flag= js.getInt("success")
                        var message= js.getString("message")
                        var arr= js.getJSONArray("Leave")

                        Log.d("flag",flag.toString())
                        Log.d("msg",message.toString())

                        for(i in 0 until arr.length()){
                            Log.v("i",i.toString())
                            var la=arr.getJSONObject(i)

                            var leave_id=la.getString("leave_id")
                            var fromDate = la.getString("fromDate")
                            var toDate = la.getString("toDate")
                            var type1 = la.getString("type1")
                            var type2 = la.getString("type2")
                            var reason = la.getString("reason")
                            var status = la.getString("status")

                            userobj = PocoLeavesResponses(leave_id, fromDate, toDate, type1, type2, reason, status)
                            arrUser2.add(userobj)
                            Log.v("end","in the end")
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    /*fun callService(emp_id:String) {
        try{
            val client = OkHttpClient()

            val formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .build()

            val request = Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/view_leaves_responses.php")
                .post(formBody)
                .build()

            Log.v("r1","Middle")
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("r2","onResponse")
                        var data = response.body!!.string()
                        Log.d("retrive", data)

                        var obj = JSONObject(data)
                        var flag= obj.getInt("success")
                        var msg= obj.getString("message")
                        var uarr= obj.getJSONArray("Leave")

                        Log.v("flag",flag.toString())
                        Log.v("msg",msg.toString())

                        for(i in 0 until uarr.length()) {
                            Log.v("lop", "In loop")
                            var ua = uarr.getJSONObject(i)

                            var leave_id = ua.getString("leave_id")
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

                            userobj = PocoLeavesResponses(leave_id, fromDate, toDate, type1, type2, reason, status)
                            arrUser2.add(userobj)
                            Log.d("arr", arrUser2.toString())
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
    }*/
}
