package com.example.salesrecord

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import kotlinx.android.synthetic.main.activity_register_shop.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.math.BigDecimal

class RegisterShop : AppCompatActivity() {
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var LocLat: BigDecimal = 0.toBigDecimal()
    private var LocLong: BigDecimal = 0.toBigDecimal()
    private lateinit var locationManager: LocationManager

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_shop)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var cmpId=preference.getString("uname","Wrong").toString()

        btnGetShopLoc.setOnClickListener {
            runOnUiThread {
                getLocation()
            }
        }

        btnRegisterShop.setOnClickListener{
            Toast.makeText(this@RegisterShop, cmpId, Toast.LENGTH_LONG).show()
            if((shopName.text.toString() == " ") || (shopLocLat.text.toString() == " ") || (shopLocLong.text.toString() == " ") || (cmpId == "")){
                Toast.makeText(this@RegisterShop, "Required fields are missing.", Toast.LENGTH_LONG).show()
            }else{
                var sName=shopName.text.toString()
                var sLocLat=shopLocLat.text.toString()
                var sLocLong=shopLocLong.text.toString()
                callService(cmpId, sName, sLocLat, sLocLong)
            }
        }
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
                            //var lon=locationGps!!.latitude.toLong()
                            LocLat = locationGps!!.latitude.toBigDecimal()
                            LocLong = locationGps!!.longitude.toBigDecimal()

                            //val builder = AlertDialog.Builder(this@RegisterShop)
                            //builder.setMessage("\nLatitude : $LocLat\nLongitude : $LocLong")
                            //val show = builder.show()
                            shopLocLat.isEnabled=false
                            shopLocLong.isEnabled=false

                            shopLocLat.setText("$LocLat")
                            shopLocLong.setText("$LocLong")
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

    fun callService(cmpId:String, sName:String, sLocLat:String, sLocLong:String) {
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("comp_id",cmpId)
                .add("sName",sName)
                .add("sLocLat",sLocLat)
                .add("sLocLong",sLocLong)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.70/SalesRecord/addShop.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check","abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.v("test",str)

                        var jObj = JSONObject(str)
                        val flag=jObj.getInt("success")
                        var message=jObj.getString("message")

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)

                        if(flag == 1){
                            runOnUiThread {
                                Log.v("fs", flag.toString())
                                Toast.makeText(this@RegisterShop,"Shop Registered Successful.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@RegisterShop, HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@RegisterShop,"Failed to Register.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}
