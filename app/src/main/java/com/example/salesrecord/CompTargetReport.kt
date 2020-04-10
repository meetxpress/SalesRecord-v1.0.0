package com.example.salesrecord

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comp_target_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class CompTargetReport : AppCompatActivity() {

    var arrUser = ArrayList<PocoCompTargetReport>()
    var userobj:PocoCompTargetReport = PocoCompTargetReport("","", "")

    lateinit var adap: ArrayAdapter<PocoCompTargetReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_target_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        //targetMon.setText(SimpleDateFormat("M-YYYY").format(Calendar.getInstance().time)).toString()
        targetMon.setText("03-2020")

        targetMon.setOnClickListener{
            arrUser.clear()
        }

        btnGenCompTarget.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            if(targetMon.text.toString() == " "){
                Toast.makeText(this@CompTargetReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()
            }else{
                arrUser.clear()
                callService(comp_id, targetMon.text.toString())
                adap = ArrayAdapter<PocoCompTargetReport>(this@CompTargetReport, android.R.layout.simple_list_item_1, arrUser)
                displayCompTargetReport.adapter = adap
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
                .url("http://192.168.43.231/SalesRecord/callCompTargetService.php")
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
                        var arr= js.getJSONArray("Target")

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var mon = ua.getString("mon") as String
                            var atarget = ua.getString("atarget") as String
                            var p_target = ua.getString("p_target") as String

                            userobj = PocoCompTargetReport(mon, atarget, p_target)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@CompTargetReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@CompTargetReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
