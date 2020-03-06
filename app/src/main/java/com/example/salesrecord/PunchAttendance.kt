package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PunchAttendance : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_punch_attendance)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

    }
}
