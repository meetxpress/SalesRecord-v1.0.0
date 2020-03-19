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

class SalarySummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_summary)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id=preference.getString("uname","Wrong").toString()

        btnShowTarget.setOnClickListener {
            callService(emp_id)
        }
        btnRefreshSal.setOnClickListener {
            callServiceSalary(emp_id)
        }
    }

    fun callService(emp_id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/fetchtarget1.php")
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
                        var atarget=js.getString("atarget")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Target Refreshed", Toast.LENGTH_LONG).show()
                                tvTarget.setText(atarget).toString()
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

    fun callServiceSalary(emp_id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/fetchsalary.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception1",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        var js= JSONObject(str)
                        var flag=js.getInt("success")
                        var msg=js.getString("message")

                        //filling data in EditText
                        var esal=js.getString("esal")
                        Log.v("res1",str)

                        if(flag == 1){
                            Log.v("fs1", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@SalarySummaryActivity,"Salary Refreshed", Toast.LENGTH_LONG).show()
                                tvbasicsal.setText(esal).toString()
                            }
                        }else{
                            Log.v("ff1", flag.toString())
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
