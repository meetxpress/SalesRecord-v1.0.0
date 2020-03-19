package com.example.salesrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

private const val PERMISSION_REQUEST = 10
class PunchAttendance : AppCompatActivity() {

    var att1:Char = ' '
    var att2:Char = ' '
    var pi_date = " "
    var pi_time = " "
    var pi_locLat:String=" "
    var pi_locLong:String= " "

    var po_time = " "
    var po_locLat:String=" "
    var po_locLong:String = " "

    var LocLat:BigDecimal = 0.toBigDecimal()
    var LocLong:BigDecimal = 0.toBigDecimal()
    var flag:Int = 1

    var x:String=" "
    var y:String=" "

    private var hasGps = false
    private var locationGps: Location? = null

    private lateinit var locationManager: LocationManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo



   private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        //setting date and time as title of the card
        val tdate= (SimpleDateFormat("d-M-Y").format(Calendar.getInstance().time))
        date1.text = tdate

        //biometric module
        executor = ContextCompat.getMainExecutor(this@PunchAttendance)
        biometricPrompt = BiometricPrompt(this@PunchAttendance, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    flag += 1
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        )

        promptInfo = PromptInfo.Builder()
            .setTitle("Punch Your Attendance")
            .setNegativeButtonText("Cancel")
            .build()

       // Create persistent LocationManager reference
       locationManager = (getSystemService(LOCATION_SERVICE) as LocationManager?)!!

        btnAtt1.setOnClickListener {
            var stf = SimpleDateFormat("hh:mm")
            var curTime = stf.format(Date())
            textView2.text = "Time: $curTime"

            biometricPrompt.authenticate(promptInfo)
            if(flag == 1){
                btnAtt1.isEnabled = false
                btnAtt1.isClickable = false
                Log.v("cp", "CheckPoint")

                pi_date = "$tdate"
                pi_time = "$curTime"

                //getLocation()
                //pi_locLat = 37.421998333.toBigDecimal()
                //pi_locLong = (-122.0840000).toBigDecimal()

                //pi_locLat.toString()
                //pi_locLong.toString()

                Toast.makeText(this@PunchAttendance, "$x" , Toast.LENGTH_LONG).show()

                //val builder = AlertDialog.Builder(this@PunchAttendance)
                //builder.setMessage("\nLatitude : $pi_locLat\nLongitude : $pi_locLong")
                //builder.show()

                Log.v("x1", "$x")
                Log.v("y1", "$y")
            }
        }

        btnAtt2.setOnClickListener{
            var stf = SimpleDateFormat("hh:mm")
            var curTime2 = stf.format(Date())
            textView6.text = "Time: $curTime2"

            biometricPrompt.authenticate(promptInfo)
            if(flag == 2){
                btnAtt2.isEnabled = false
                btnAtt2.isClickable = false
                Log.v("cp2", "CheckPoint")

                po_time = "$curTime2"
                //getLocation()
                Toast.makeText(this@PunchAttendance, "Punch-Out done Successfully." , Toast.LENGTH_LONG).show()
               // po_locLat = 37.421998333.toBigDecimal()
              //  po_locLong = (-122.0840000).toBigDecimal()

                callService(emp_id, pi_date, pi_time, pi_locLat, pi_locLong, po_time, po_locLat, po_locLong)
                Log.v("done2", "Done2")
            }
            flag = 0
        }
    }

    fun callService(emp_id:String, pi_date:String, pi_time:String, pi_locLat:String, pi_locLong:String, po_time:String, po_locLat:String, po_locLong:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .add("pi_date", pi_date)
                .add("pi_time", pi_time)
                .add("pi_locLat", pi_locLat)
                .add("pi_locLong", pi_locLong)
                .add("po_time", po_time)
                .add("po_locLat", po_locLat)
                .add("po_locLong", po_locLong)
                .build()

                /*Log.v("emp_id", emp_id)
                Log.v("pi_date", pi_date)
                Log.v("pi_time", pi_time)
                Log.v("pi_locLat", pi_locLat)
                Log.v("pi_locLong", pi_locLong)
                Log.v("po_time", po_time)
                Log.v("po_locLat", po_locLat)
                Log.v("po_locLong", po_locLong)*/

            var req= Request.Builder()
                .url("http://192.168.43.231/SalesRecord/attendance.php")
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
                                Toast.makeText(this@PunchAttendance,"Attendance is punched.", Toast.LENGTH_LONG).show()
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

    @SuppressLint("MissingPermission")
    //getting location in Lang-Long
    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (hasGps) {
            Log.d("CodeAndroidLocation", "hasGps")
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1F, object :
                LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                       // runOnUiThread {
                            LocLat = locationGps!!.latitude.toBigDecimal()
                            LocLong = locationGps!!.longitude.toBigDecimal()
                        runOnUiThread {
                            add_LocLat.setText("$LocLat")
                            add_LocLong.setText("$LocLong")

                            pi_locLat=LocLat.toString()
                            pi_locLong=LocLong.toString()


                            Log.v("loc","Done")
                            Log.d("lati", " GPS Latitude : " + LocLat.toString())
                            Log.d("long", " GPS Longitude : " + LocLong.toString())
                        }

                        //val builder = AlertDialog.Builder(this@PunchAttendance)
                        //builder.setMessage("\nLatitude : "+textView31.text.toString()+"\nLongitude : "+textView32.text.toString())
                        //builder.show()

                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    runOnUiThread {
                        LocLat = locationGps!!.latitude.toBigDecimal()
                        LocLong= locationGps!!.longitude.toBigDecimal()
                        Log.v("loc","Done")

                        LocLat=locationGps!!.latitude.toBigDecimal()
                        LocLong=locationGps!!.longitude.toBigDecimal()
                    }
                }
                override fun onProviderEnabled(provider: String?) {}
                override fun onProviderDisabled(provider: String?) {}
            })

            val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (localGpsLocation != null){
                locationGps = localGpsLocation
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

}