package com.example.salesrecord

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_salary_summary.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class SalarySummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_summary)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        val date= Calendar.getInstance()
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnShowTarget.setOnClickListener {
            var cmonth= SimpleDateFormat("MM-YYYY").format(date.time)
            //var cmonth="03-2020"
            callService(emp_id, cmonth)
        }
        btnRefreshSal.setOnClickListener {
            var smonth= SimpleDateFormat("MM-YYYY").format(date.time)
            //var smonth="03-2020"

            callServiceSalary(emp_id, smonth)
        }
    }

    fun callService(id:String,cmonth:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",id)
                .add("cmonth",cmonth)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/fetchtarget1.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        var js= JSONObject(str)
                        var flag=js.getInt("success")
                        var msg=js.getString("message")

                        //filling data in EditText
                        var p_target=js.getString("p_target")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Target Refreshed", Toast.LENGTH_LONG).show()
                                tvTarget.setText(p_target).toString()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Error!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun callServiceSalary(id:String,smonth:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",id)
                .add("smonth",smonth)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/getsal.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        Log.v("x",str)
                        var ojs= JSONObject(str)
                        var flag=ojs.getInt("success")
                        var msg=ojs.getString("message")

                        //filling data in EditText
                        var emp_bsal=ojs.getInt("emp_bsal")
                        var emp_inc=ojs.getInt("emp_inc")
                        var emp_totsal=ojs.getString("emp_totsal")
                        Log.v("res",str.toString())

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Salary Refreshed", Toast.LENGTH_LONG).show()
                                tvbasicsal.setText(emp_bsal.toString()).toString()
                                tvInc.setText(emp_inc.toString()).toString()
                                tvtotsal.setText(emp_totsal.toString()).toString()

                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Error!", Toast.LENGTH_LONG).show()
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
