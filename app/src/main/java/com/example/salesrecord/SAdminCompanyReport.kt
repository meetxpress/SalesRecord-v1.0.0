package com.example.salesrecord

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_s_admin_company_report.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SAdminCompanyReport : AppCompatActivity() {
    var arrUser2 = ArrayList<PocoSupAdminCompanyReport>()
    var userobj:PocoSupAdminCompanyReport = PocoSupAdminCompanyReport("", "", "","","","")

    lateinit var adap:ArrayAdapter<PocoSupAdminCompanyReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_admin_company_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var uname = preference.getString("uname","Wrong").toString()

        btnRefreshCompanyReport.setOnClickListener{
            arrUser2.clear()
            callService(uname)
            adap = ArrayAdapter<PocoSupAdminCompanyReport>(this@SAdminCompanyReport, android.R.layout.simple_list_item_1, arrUser2)
            displayCompanyReport.adapter = adap
            adap.notifyDataSetChanged()
        }
    }

    fun callService(uname:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("uname", uname)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/callSuperAdminCompanyService.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
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
                        var arr= js.getJSONArray("Comp")
                        Log.v("al",arr.length().toString())

                        for(i in 0 until arr.length()){
                            Log.v("i", i.toString())
                            var la=arr.getJSONObject(i)

                            var comp_id=la.getString("comp_id")
                            var comp_name = la.getString("comp_name")
                            var comp_city = la.getString("comp_city")
                            var comp_mob1= la.getString("comp_mob1")
                            var comp_person= la.getString("comp_person")
                            var comp_status = la.getString("comp_status")

                            userobj = PocoSupAdminCompanyReport(comp_id, comp_name, comp_city, comp_mob1, comp_person, comp_status)
                            arrUser2.add(userobj)
                            Log.v("end","in the end")
                            runOnUiThread {
                                adap.notifyDataSetChanged()
                            }
                        }
                    }
                }
            })
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
