package com.example.salesrecord

import android.hardware.biometrics.BiometricManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import java.text.SimpleDateFormat
import java.util.*

class PunchAttendance : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val sdf = SimpleDateFormat("dd/mm/yy")
        val currentDate = sdf.format(Date())

        date1.text=currentDate

        btnAtt1_1.setOnClickListener {
            val stf = SimpleDateFormat("hh:mm")
            val curTime = stf.format(Date())
            textView2.text = "Time: $curTime"
            btnAtt1_1.isEnabled=false
            btnAtt1_1.isClickable=false


            /* *
            * fingerprint authentication
            * */
        }
    }
}
