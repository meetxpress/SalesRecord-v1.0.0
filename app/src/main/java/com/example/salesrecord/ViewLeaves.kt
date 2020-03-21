package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_leaves.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ViewLeaves : AppCompatActivity() {

    var arrUser = ArrayList<Leaves>()
    var userobj:Leaves = Leaves("","","","","","","")

    lateinit var adap:ArrayAdapter<Leaves>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_leaves)

        //back button on actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        btnRefreshLeave.setOnClickListener{
            callservice(comp_id)
             Toast.makeText(this@ViewLeaves, comp_id,Toast.LENGTH_LONG).show()
            adap = ArrayAdapter<Leaves>(this@ViewLeaves, android.R.layout.simple_list_item_1, arrUser)
            dispLeaves.adapter = adap

            adap.notifyDataSetChanged()
        }
    }

    fun callservice(comp_id:String) {
        try{
            val client = OkHttpClient()

            val formBody=FormBody.Builder()
                .add("comp_id",comp_id)
                .build()

            val request = Request.Builder()
            .url("http://10.0.2.2:80/SalesRecord/view_leaves.php")
                .post(formBody)
                .build()
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var data = response.body!!.string()
                        Log.d("retrive", data)

                        var obj = JSONObject(data)
                        var flag= obj.getInt("success")
                        var msg= obj.getString("message")
                        var uarr= obj.getJSONArray("Leave")

                        Log.d("flag",flag.toString())
                        Log.d("msg",msg.toString())

                        for(i in 0 until uarr.length()) {
                            Log.v("loop", "In loop")
                            var ua = uarr.getJSONObject(i)

                            var emp_id = ua.getString("emp_id")
                            var fromDate = ua.getString("fromDate")
                            var toDate = ua.getString("toDate")
                            var type1 = ua.getString("type1")
                            var type2 = ua.getString("type2")
                            var reason = ua.getString("reason")
                            var status = ua.getString("status")

                            if((emp_id =="") &&
                                (fromDate =="") &&
                                (toDate =="") &&
                                (type1  =="") &&
                                (type2  =="") &&
                                (reason =="") &&
                                (status =="")
                            ){

                            }

                            userobj = Leaves(emp_id, fromDate, toDate, type1, type2, reason, status)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
        }catch (e: Exception)
        {
            Log.d("Exception",e.toString())
        }
    }
}
