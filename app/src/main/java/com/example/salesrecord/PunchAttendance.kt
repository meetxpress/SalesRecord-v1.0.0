package com.example.salesrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
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
    var emp_id = ""

    //var LocLat: BigDecimal = 0
    //var LocLong: BigDecimal = 0

    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null

    private lateinit var locationManager: LocationManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        //biometric module
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        val date=Calendar.getInstance()
        date1.text = (SimpleDateFormat("d - M - Y").format(date.time))

        btnAtt1.setOnClickListener {
            val stf = SimpleDateFormat("hh:mm")
            val curTime = stf.format(Date())
            textView2.text = "Time: $curTime"

            att1 = 'P'
            att2 = 'A'
            callService(att1, att2)
            getLocation()
            Toast.makeText(this@PunchAttendance, "$att1 & $att2" , Toast.LENGTH_LONG).show()
            att1=' '
            att2=' '

            biometricPrompt.authenticate(promptInfo)
            callService(att1, att2)
        }

        btnAtt2.setOnClickListener {
            /*val stf = SimpleDateFormat("hh:mm")
            val curTime = stf.format(Date())
            textView2.text = "Time: $curTime"

            att1 = 'P'
            att2 = 'P'
            callService(att1, att2)
            getLocation()
            Toast.makeText(this@PunchAttendance, "$att1 & $att2" , Toast.LENGTH_LONG).show()
            att1 = ' '
            att2 = ' '*/

            biometricPrompt.authenticate(promptInfo)
            callService(att1,att2)
        }
    }

    fun callService(att1: Char, att2: Char){
        // comp_id:String
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .add("att1", att1.toString())
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

    private fun enableView() {
        btnAtt2.isEnabled = true
        btnAtt2.alpha = 1F
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
    }

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
                            var lon=locationGps!!.latitude.toLong()
                            var LocLat: BigDecimal = locationGps!!.latitude.toBigDecimal()
                            var LocLong: BigDecimal = locationGps!!.longitude.toBigDecimal()

                            //    val builder = AlertDialog.Builder(this@PunchAttendance)
                            //    builder.setMessage("\nLatitude : $LocLat\nLongitude : $LocLong")
                            //    builder.show()

                            //textView6.append("\nGPS ")
                            Log.d("lati", " GPS Latitude : " + locationGps!!.latitude)
                            Log.d("long", " GPS Longitude : " + locationGps!!.longitude)
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