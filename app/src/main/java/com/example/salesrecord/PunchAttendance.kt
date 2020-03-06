package com.example.salesrecord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_punch_attendance.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext.plus
import kotlin.coroutines.EmptyCoroutineContext.plus

class PunchAttendance : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        val sdf = SimpleDateFormat("dd/mm/yy")
        val currentDate = sdf.format(Date())

        val stf = SimpleDateFormat("hh:mm")
        val curTime = stf.format(Date())

        date1.text=currentDate

        btnAtt1_1.setOnClickListener {
           textView2.text = "Time: $curTime"
        }
    }
}
