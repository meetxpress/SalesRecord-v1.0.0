package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_view_leaves.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ViewLeaves : AppCompatActivity() {

    var arrUser = ArrayList<PocoLeaves>()
    var userobj:PocoLeaves = PocoLeaves("","","","","","","","")

    var status:String = " "
    var emp_id:String=" "
    var comp_id:String=""
    var leave_id:String=" "

    lateinit var adap:ArrayAdapter<PocoLeaves>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_leaves)

        //back button on actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        comp_id = preference.getString("uname","Wrong").toString()

        btnRefreshLeave.setOnClickListener{
            arrUser.clear()
            callService(comp_id)
            adap = ArrayAdapter<PocoLeaves>(this@ViewLeaves, android.R.layout.simple_list_item_1, arrUser)
            dispLeaves.adapter = adap
            adap.notifyDataSetChanged()
        }

        dispLeaves.setOnItemClickListener { adapterView, _, position: Int, _: Long ->
            val ss = adapterView.getItemAtPosition(position).toString()
            val builder = AlertDialog.Builder(this@ViewLeaves)

            builder.setTitle("Leave Application")
            builder.setMessage(ss)

            builder.setPositiveButton("Approve") { _, _ ->
                runOnUiThread {
                    //Toast.makeText(this@ViewLeaves, "Accepted", Toast.LENGTH_SHORT).show()
                    status="Approve"
                    callMainService(emp_id, leave_id, status)
                }
            }

            builder.setNegativeButton("Reject") { _, _ ->
                runOnUiThread {
                    //Toast.makeText(this@ViewLeaves, "Rejected", Toast.LENGTH_SHORT).show()
                    status="Reject"
                    callMainService(emp_id, leave_id, status)
                }
            }
            builder.show()
        }
    }

    fun callService(comp_id:String) {
        try{
            val client = OkHttpClient()

            val formBody=FormBody.Builder()
                .add("comp_id",comp_id)
                .build()

            val request = Request.Builder()
            .url("http://192.168.43.215/SalesRecord/view_leaves.php")
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

                            emp_id = ua.getString("emp_id")
                            leave_id = ua.getString("leave_id")
                            var fromDate = ua.getString("fromDate")
                            var toDate = ua.getString("toDate")
                            var type1 = ua.getString("type1")
                            var type2 = ua.getString("type2")
                            var reason = ua.getString("reason")
                            var status = ua.getString("status")

                            userobj = PocoLeaves(emp_id, leave_id, fromDate, toDate, type1, type2, reason, status)
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

    fun callMainService(emp_id:String, leave_id:String, status:String){
        try{
            val client = OkHttpClient()

            val formBody=FormBody.Builder()
                .add("emp_id",emp_id)
                .add("leave_id",leave_id)
                .add("status",status)
                .build()

            val request = Request.Builder()
                .url("http://192.168.43.215/SalesRecord/approve_leaves.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var data = response.body!!.string()
                        Log.v("retrive", data)

                        var obj = JSONObject(data)
                        var flag= obj.getInt("success")
                        var message= obj.getString("message")

                        Log.v("flag",flag.toString())
                        Log.v("msg",message.toString())

                        if(flag==1){
                            Log.v("fs",flag.toString())
                            runOnUiThread {
                                Toast.makeText(this@ViewLeaves, "Approved", Toast.LENGTH_SHORT).show()
                                dispLeaves.adapter=null
                            }
                        }else{
                            Log.v("fs",flag.toString())
                            runOnUiThread {
                                Toast.makeText(this@ViewLeaves, "Rejected", Toast.LENGTH_SHORT).show()
                                dispLeaves.adapter=null
                            }
                        }
                        callService(comp_id)
                    }
                }
            })
        } catch(e:Exception){
            e.printStackTrace()
        }
    }

}
