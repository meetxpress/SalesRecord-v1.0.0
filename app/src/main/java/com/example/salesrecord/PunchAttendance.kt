package com.example.salesrecord

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import kotlinx.android.synthetic.main.activity_login.*
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

    var long1: String = " "
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname", "Wrong").toString()

        disableView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                enableView()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            enableView()
        }

        btnAtt2.setOnClickListener {
            Toast.makeText(this@PunchAttendance, "LOL", Toast.LENGTH_SHORT).show()
            getLocation()
            var sLocLat = pi_loc_lat.text.toString()
            var sLocLong = pi_loc_long.text.toString()
            Log.d("lat1", sLocLat)
            Log.d("long1", sLocLong)
            callService(emp_id, sLocLat, sLocLong)
        }
    }

    fun callService(emp_id: String, sLocLat: String, sLocLong: String) {
        try {
            var client = OkHttpClient()

            var formBody = FormBody.Builder()
                .add("emp_id", emp_id)
                .add("po_lat", sLocLat)
                .add("po_long", sLocLong)
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
                                Toast.makeText(this@PunchAttendance, "Punched Successful.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@PunchAttendance, "Failed to Punch.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun disableView() {
        btnAtt1.isEnabled = false
        btnAtt1.alpha = 0.5F
    }
    private fun enableView() {
        btnAtt1.isEnabled = true
        btnAtt1.alpha = 1F
        getLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 11F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location
                            //  tv_result.append("\nGPS ")

                            pi_loc_lat.append("" + locationGps!!.latitude)
                            pi_loc_long.append("," + locationGps!!.longitude)
                            runOnUiThread{
                                long1=pi_loc_lat.toString()+","+pi_loc_long.toString()
                                Log.d("temp","fullString")
                            }
                            Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                            Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String?) {}
                    override fun onProviderDisabled(provider: String?) {}
                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if(locationGps!= null && locationNetwork!= null){
                if(locationGps!!.accuracy > locationNetwork!!.accuracy){
                    //     tv_result.append("\nNetwork ")
                    pi_loc_lat.append("" + locationNetwork!!.latitude)
                    pi_loc_long.append("," + locationNetwork!!.longitude)
                    runOnUiThread{
                        long1=pi_loc_lat.toString()+","+pi_loc_long.toString()
                    }

                    Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                    Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                }else{
                    // tv_result.append("\nGPS ")
                    pi_loc_lat.append("" + locationGps!!.latitude)
                    pi_loc_long.append("" + locationGps!!.longitude)

                    runOnUiThread{
                        long1=pi_loc_lat.toString()+","+pi_loc_long.toString()
                    }
                    Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                    Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                enableView()
        }
        //meet code

    }
}







