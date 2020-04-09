package com.example.salesrecord

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_apply_for_leave.*
import kotlinx.android.synthetic.main.activity_home_employee.*
import kotlinx.android.synthetic.main.activity_register_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ApplyForLeave : AppCompatActivity() {
    var fd: String = " "
    var td: String = " "

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_for_leave)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        val tdate= (SimpleDateFormat("dd-MM-YYYY").format(Calendar.getInstance().time))

        var c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        fromDate.setOnClickListener {
            var dpd=DatePickerDialog(this@ApplyForLeave,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay->
                fd=("$mDay-$mMonth-$mYear")
                fromDate.setText(fd).toString()
            }, year, month, day)
            dpd.show()
        }

        var c1 = Calendar.getInstance()
        var year1 = c1.get(Calendar.YEAR)
        var month1 = c1.get(Calendar.MONTH)
        var day1 = c1.get(Calendar.DAY_OF_MONTH)
        toDate.setOnClickListener {
            var tDate = DatePickerDialog(this@ApplyForLeave, DatePickerDialog.OnDateSetListener { view1, mYear1, mMonth1, mDay1 ->
                    td = ("$mDay1-$mMonth1-$mYear1")
                    toDate.setText(td).toString()

                }, year1, month1, day1)

            tDate.show()
        }

        btnSubmit.setOnClickListener {
            //database code
            if((fromDate.toString().length<0) and
                (toDate.toString().length<0) and
                (spinner.toString().length<0) and
                (spinner1.toString().length<0) and
                (spinner2.toString().length<0))
            {
                Toast.makeText(this@ApplyForLeave,"Required Fields are missing.", Toast.LENGTH_LONG).show()
            } else {
                var type=spinner.selectedItem.toString()
                var type2:String=spinner1.selectedItem.toString()
                var reason:String = spinner2.selectedItem.toString()
                var fDate = fromDate.text.toString()
                var tDate = toDate.text.toString()
                //Toast.makeText(this,"Leave Applied Successfully.", Toast.LENGTH_LONG).show()
                callService(emp_id, fDate, tDate, type, type2, reason, "Pending",tdate)
            }
        }

        btnCancel.setOnClickListener {
            var i = Intent(this@ApplyForLeave,HomeEmployee::class.java)
            startActivity(i)
        }

        btnViewLeaveResponse.setOnClickListener{
            var i = Intent(this@ApplyForLeave,ViewLeaveResponse::class.java)
            startActivity(i)
        }
    }

    fun callService(emp_id:String, fDate:String, tDate:String, type:String, type2:String, reason:String, status:String, tdate:String) {
        try {
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .add("fDate",fDate)
                .add("tDate",tDate)
                .add("type",type)
                .add("type2",type2)
                .add("reason",reason)
                .add("status",status)
                .add("tdate",tdate)
                .build()

            var request= Request.Builder()
                .url("http://192.168.43.70/SalesRecord/apply_leave.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        Log.v("check","abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.v("test",str)

                        var jsonObj = JSONObject(str)
                        val flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message").toString()

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)
                        if(flag == 1){
                            runOnUiThread {
                                Log.v("fs", flag.toString())
                                Toast.makeText(this@ApplyForLeave, message, Toast.LENGTH_LONG).show()
                                var i= Intent(this@ApplyForLeave,HomeEmployee::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@ApplyForLeave, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch (e:Exception){
        e.printStackTrace()
        }
    }
}
