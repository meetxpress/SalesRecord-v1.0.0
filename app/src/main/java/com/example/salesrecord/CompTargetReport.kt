package com.example.salesrecord

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CompTargetReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp_target_report)
        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()
    }
}
