package com.example.salesrecord

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class PunchAttendance : AppCompatActivity() {
    private var locationManager : LocationManager? = null

    var flag:Int = 1
    var p_long:String=""
    var p_lat:String=""


    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    private lateinit var executor1: Executor
    private lateinit var biometricPrompt1: BiometricPrompt
    private lateinit var promptInfo1: BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname", "Wrong").toString()

        //location code
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }


        //date
        var tdate= (SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time))
        date1.text = tdate

        //biometric code
        executor = ContextCompat.getMainExecutor(this@PunchAttendance)
        biometricPrompt = BiometricPrompt(this@PunchAttendance, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@PunchAttendance, "Authentication1 error: $errString", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    //flag += 1
                    var stf = SimpleDateFormat("hh:mm")
                    var curTime = stf.format(Date())
                    callServicein(emp_id, p_lat, p_long, tdate, curTime)
                    //Toast.makeText(this@PunchAttendance, "Authentication1 success", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@PunchAttendance, "Authentication1 failed", Toast.LENGTH_SHORT).show()
                }
            }
        )
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Punch Your Attendance")
            .setNegativeButtonText("Cancel")
            .build()


        executor1 = ContextCompat.getMainExecutor(this@PunchAttendance)
        biometricPrompt1 = BiometricPrompt(this@PunchAttendance, executor1,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@PunchAttendance, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    //flag += 1
                    var stf = SimpleDateFormat("hh:mm")
                    var curTime = stf.format(Date())
                    callServiceout(emp_id, p_lat, p_long, tdate,curTime)
                    Log.d("emp_id","$emp_id 2")
                    Log.d("p_lat",p_lat)
                    Log.d("p_long",p_long)
                    Log.d("tdate",tdate)
                    Log.d("curTime",curTime)
                    //Toast.makeText(this@PunchAttendance, "Authentication success", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@PunchAttendance, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        )
        promptInfo1 = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Punch Your Attendance")
            .setNegativeButtonText("Cancel")
            .build()

        btnAtt1.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
        btnAtt2.setOnClickListener {
            biometricPrompt1.authenticate(promptInfo1)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
       //     pi_loc_lat.text = ("" + location.longitude + ":" + location.latitude)
            p_lat=location.latitude.toString()
            p_long=location.longitude.toString()

        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun callServicein(emp_id: String, sLocLat: String, sLocLong: String, pi_date: String, pi_time: String) {
        try {
            var client = OkHttpClient()

            var formBody = FormBody.Builder()
                .add("emp_id", emp_id)
                .add("pi_lat", sLocLat)
                .add("pi_long", sLocLong)
                .add("pi_date", pi_date)
                .add("pi_time", pi_time)
                .build()

            Log.d("emp_id",emp_id)
            Log.d("p_lat",p_lat)
            Log.d("p_long",p_long)


            var req = Request.Builder()
                .url("http://192.168.43.70/SalesRecord/punchattendancein.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check", "abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str = response.body!!.string()
                        Log.v("test", str)

                        var jObj = JSONObject(str)
                        val flag = jObj.getInt("success")
                        var message = jObj.getString("message")

                        Log.v("res", response.toString())
                        Log.v("cdm", flag.toString())
                        Log.v("msg", message)

                        if (flag == 1) {
                            runOnUiThread {
                                Log.v("fs", flag.toString())
                                Toast.makeText(this@PunchAttendance, "Attendance Punched.", Toast.LENGTH_LONG).show()
                                var i= Intent(this@PunchAttendance,HomeEmployee::class.java)
                                startActivity(i)
                            }
                        } else {
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@PunchAttendance, "Attendance Fail.", Toast.LENGTH_LONG).show()
                                var i= Intent(this@PunchAttendance,HomeEmployee::class.java)
                                startActivity(i)
                            }
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun callServiceout(emp_id: String, sLocLat: String, sLocLong: String, pi_date: String, pi_time: String) {
        try {
            var client = OkHttpClient()

            var formBody = FormBody.Builder()
                .add("emp_id", emp_id)
                .add("pi_lat", sLocLat)
                .add("pi_long", sLocLong)
                .add("pi_date", pi_date)
                .add("pi_time", pi_time)
                .build()

            var req = Request.Builder()
                .url("http://192.168.43.70/SalesRecord/punchattendanceout.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check", "abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str = response.body!!.string()
                        Log.v("test", str)

                        var jObj = JSONObject(str)
                        val flag = jObj.getInt("success")
                        var message = jObj.getString("message")

                        Log.v("res", response.toString())
                        Log.v("cdm", flag.toString())
                        Log.v("msg", message)

                        if (flag == 1) {
                            runOnUiThread {
                                Log.v("fs", flag.toString())
                                Toast.makeText(this@PunchAttendance, "Attendance Punched.", Toast.LENGTH_LONG).show()
                                var i= Intent(this@PunchAttendance, HomeEmployee::class.java)
                                startActivity(i)
                            }
                        } else {
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@PunchAttendance, "Attendance Fail.", Toast.LENGTH_LONG).show()
                                var i= Intent(this@PunchAttendance, HomeEmployee::class.java)
                                startActivity(i)
                            }
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

