package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_comp_sales_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CompSalesReport : AppCompatActivity() {

    var arrUser = ArrayList<PocoCompSalesReport>()
    var userobj:PocoCompSalesReport = PocoCompSalesReport( "","")

    lateinit var adap: ArrayAdapter<PocoCompSalesReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_sales_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)
        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        empSalesMonth.setText(SimpleDateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
        //empSalesMonth.setText("03-2020")
        empSalesMonth.setOnClickListener{
            arrUser.clear()
        }

        btnGenCompSales.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if(empSalesMonth.text.toString() == " "){
                Toast.makeText(this@CompSalesReport,"Required Fields are missing.", Toast.LENGTH_SHORT).show()
            }else{
                arrUser.clear()
                callService(comp_id, empSalesMonth.text.toString())
                adap = ArrayAdapter<PocoCompSalesReport>(this@CompSalesReport, android.R.layout.simple_list_item_1, arrUser)
                displayCompSalesReport.adapter = adap
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
                .url("http://192.168.43.215/SalesRecord/callCompSalesService.php")
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
                        var arr= js.getJSONArray("Sales")

                        for(i in 0 until arr.length()) {
                            var ua = arr.getJSONObject(i)

                            var totSales = ua.getString("totSales")
                            var sal_month = ua.getString("mon")

                            userobj = PocoCompSalesReport(totSales, sal_month)
                            arrUser.add(userobj)
                            Log.d("arr", arrUser.toString())
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                        runOnUiThread{
                            if(flag==1){
                                Toast.makeText(this@CompSalesReport, "Report is Generated.",Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@CompSalesReport, "No Data Found.",Toast.LENGTH_SHORT).show()
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
