package com.example.salesrecord

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import kotlinx.android.synthetic.main.activity_punch_attendance.textView2
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PunchAttendance : AppCompatActivity() {

    private var locationManager : LocationManager? = null

    var att1:Int = 0
    var att2:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val date=Calendar.getInstance()
        date1.text = (SimpleDateFormat("d - M - Y").format(date.time))

        date.add(Calendar.DATE, 1)
        date2.text = (SimpleDateFormat("d - M - Y").format(date.time))

        date.add(Calendar.DATE, 1)
        date3.text = (SimpleDateFormat("d - M - Y").format(date.time))

        date.add(Calendar.DATE, 1)
        date4.text = (SimpleDateFormat("d - M - Y").format(date.time))

        btnAtt1_1.setOnClickListener {
            val stf = SimpleDateFormat("hh:mm")
            val curTime = stf.format(Date())
            textView2.text = "Time: $curTime"
            textView3.text= Context.LOCATION_SERVICE

            //make button disable if the punch is received successfully
            //--btnAtt1_1.isEnabled=false
            //--btnAtt1_1.isClickable=false

            /*if(date != date1.text) {
                Toast.makeText(this@PunchAttendance, "Disabled", Toast.LENGTH_LONG).show()
                btnAtt1_1.isEnabled = true
                btnAtt1_1.isClickable = true
            }*/

            //att1=1
            //att2=0
            callService(att1 = 1, att2 = 0)
        }

        btnAtt1_2.setOnClickListener {
            Toast.makeText(this@PunchAttendance, "Location", Toast.LENGTH_LONG).show()
            att1 = 1
            att2 = 1
            callService(att1 = 1, att2 = 1)
        }

        /* *
        * fingerprint authentication
        * */
    }

    fun callService(att1: Int, att2: Int){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("att1",att1.toString())
                .add("att2", att2.toString())
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/attendance.php")
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

                        Log.v("res",str)
                        Log.v("cd", flag.toString())
                        Log.v("ms",msg)

                        if(flag == 1){
                            Log.v("f1", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@PunchAttendance,"Attendance1 is punched.", Toast.LENGTH_LONG).show()
                            }
                        }else if(flag == 2){
                            Log.v("f2", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@PunchAttendance,"Attendance2 is punched.", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@PunchAttendance,"Failed to Punch.", Toast.LENGTH_LONG).show()
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