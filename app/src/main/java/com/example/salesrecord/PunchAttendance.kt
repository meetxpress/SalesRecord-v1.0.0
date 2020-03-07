package com.example.salesrecord

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*

class PunchAttendance : AppCompatActivity() {

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

            //make button disable if the punch is received successfully
            btnAtt1_1.isEnabled=false
            btnAtt1_1.isClickable=false

            /*if(date != date1.text) {
                Toast.makeText(this@PunchAttendance, "Disabled", Toast.LENGTH_LONG).show()
                btnAtt1_1.isEnabled = true
                btnAtt1_1.isClickable = true
            }*/
        }



        /* *
        * fingerprint authentication
        * */
    }
}