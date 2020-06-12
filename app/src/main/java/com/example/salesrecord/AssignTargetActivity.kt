package com.example.salesrecord

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_assign_target.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AssignTargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_target)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val date= Calendar.getInstance()
        etAssignMonth.setText(SimpleDateFormat("MM-YYYY").format(date.time)).toString()

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        btnAssignTarget.setOnClickListener {
            //var assignmonth=etAssignMonth.text.toString()
            var atarget=etAssignTarget.text.toString()
            var atargetmonth=etAssignMonth.text.toString()
            callServiceUpdate(comp_id,atargetmonth,atarget)
        }
    }

    fun callServiceUpdate(comp_id:String,atargetmonth:String,atarget:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("comp_id",comp_id)
                .add("atargetmonth",atargetmonth)
                .add("atarget",atarget)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/assigntarget1.php")
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
                                etAssignMonth.text.clear()
                                etAssignTarget.text.clear()
                                Toast.makeText(this@AssignTargetActivity,"Target Assigned", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@AssignTargetActivity,"An Error Occurred!", Toast.LENGTH_LONG).show()
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
