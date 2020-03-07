package com.example.salesrecord

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import kotlinx.android.synthetic.main.activity_punch_attendance.textView2
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*

class PunchAttendance : AppCompatActivity() {

    private var locationManager : LocationManager? = null

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
            btnAtt1_1.isEnabled=false
            btnAtt1_1.isClickable=false

            /*if(date != date1.text) {
                Toast.makeText(this@PunchAttendance, "Disabled", Toast.LENGTH_LONG).show()
                btnAtt1_1.isEnabled = true
                btnAtt1_1.isClickable = true
            }*/

        }

        btnAtt1_2.setOnClickListener {
            Toast.makeText(this@PunchAttendance, "Location", Toast.LENGTH_LONG).show()

        }

        /* *
        * fingerprint authentication
        * */
    }

}