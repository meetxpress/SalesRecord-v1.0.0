package com.example.salesrecord

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_routine_task.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RoutineTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_task)

        val date= Calendar.getInstance()

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()


        btnRoutineSubmit.setOnClickListener {
            var rmonth=SimpleDateFormat("MM-YYYY").format(date.time)
            var rdate=SimpleDateFormat("d-M-Y").format(Calendar.getInstance().time)
            var rtarget=etRoutineTask.text.toString()
            callServiceSubmit(emp_id,rmonth,rtarget,rdate)
        }
    }

    fun callServiceSubmit(emp_id:String,rmonth:String,rtarget:String,rdate:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .add("rmonth",rmonth)
                .add("rtarget",rtarget)
                .add("rdate",rdate)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/routinetask3.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check","abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.v("test",str)

                        val jsonObj = JSONObject(str)
                        val flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message")

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                etRoutineTask.text.clear()
                                Toast.makeText(this@RoutineTaskActivity,"Work Submitted", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RoutineTaskActivity,"An Error Occurred!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}
