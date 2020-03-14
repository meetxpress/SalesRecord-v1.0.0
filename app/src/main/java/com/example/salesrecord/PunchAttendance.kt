package com.example.salesrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt.AuthenticationResult
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.*
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
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
    var pi_locLat:BigDecimal = 0.toBigDecimal()
    var pi_locLong:BigDecimal = 0.toBigDecimal()

    var po_time = " "
    var po_locLat:BigDecimal = 0.toBigDecimal()
    var po_locLong:BigDecimal = 0.toBigDecimal()

    var LocLat:BigDecimal = 0.toBigDecimal()
    var LocLong:BigDecimal = 0.toBigDecimal()
    var flag:String = "0"

    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null

    private lateinit var locationManager: LocationManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        //setting date and time as title of the card
        val tdate= (SimpleDateFormat("d - M - Y").format(Calendar.getInstance().time))
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
                    runOnUiThread{
                        flag = "1"
                        Toast.makeText(this@PunchAttendance, "$flag", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        btnAtt1.setOnClickListener {
            var stf = SimpleDateFormat("hh:mm")
            var curTime = stf.format(Date())
            textView2.text = "Time: $curTime"

            //biometricPrompt.authenticate(promptInfo)
            btnAtt1.isEnabled = false
            btnAtt1.isClickable = false
            Log.v("cp", "CheckPoint")

            getLocation()
            pi_date = "$tdate"
            pi_time = "$curTime"
            //pi_locLat =
            //pi_locLong =

            var builder = AlertDialog.Builder(this@PunchAttendance)
            builder.setMessage("\npi_date : $pi_date\npi_time : $pi_time\npi_locLat : $pi_locLat\npi_locLong : $pi_locLong")
            builder.show()
            Toast.makeText(this@PunchAttendance, "Punch-In done Successfully." , Toast.LENGTH_LONG).show()
        }

        btnAtt2.setOnClickListener{
            val stf = SimpleDateFormat("hh:mm")
            val curTime = stf.format(Date())
            textView6.text = "Time: $curTime"
            //getLocation()
            po_time = "$curTime"

            var builder = AlertDialog.Builder(this@PunchAttendance)
            builder.setMessage("\npi_date : $pi_date" +
                    "\npi_time : $po_time" +
                    "\npi_locLat : $po_locLat" +
                    "\npi_locLong : $po_locLong")
            builder.show()

            biometricPrompt.authenticate(promptInfo)
            if(flag == "2"){
                Log.v("p2", flag)
                Toast.makeText(this@PunchAttendance, "Punch-Out done Successfully." , Toast.LENGTH_LONG).show()
               // callService(emp_id, pi_date, pi_time, pi_locLat,pi_locLong, po_time, po_locLat, po_locLong)
            } else{
                Toast.makeText(this@PunchAttendance, "Punch-In Failed." , Toast.LENGTH_LONG).show()
            }
        }
    }

    /*fun callService(emp_id, pi_date, pi_time, pi_locLat, pi_locLong, po_time, po_locLat, po_locLong){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .add("pi_date", pi_date)
                .add("pi_time", pi_time)
                .add("pi_loc", pi_locLat)
                .add("pi_loc", pi_locLong)
                .add("po_time", po_time)
                .add("po_loc", po_locLat)
                .add("po_loc", po_locLong)
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
    }*/

    @SuppressLint("MissingPermission")
    //getting location in Lang-Long
    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1F, object :
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            //var lon=locationGps!!.latitude.toLong()
                            //LocLat = locationGps!!.latitude.toBigDecimal()
                            //LocLong = locationGps!!.longitude.toBigDecimal()

                            runOnUiThread {
                                pi_locLat = locationGps!!.latitude.toBigDecimal()
                                pi_locLong= locationGps!!.longitude.toBigDecimal()
                            }
                            val builder = AlertDialog.Builder(this@PunchAttendance)
                            builder.setMessage("\nLatitude : $pi_locLat\nLongitude : $pi_locLong")
                            builder.show()
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String?) {}
                    override fun onProviderDisabled(provider: String?) {}
                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null){
                    locationGps = localGpsLocation
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}