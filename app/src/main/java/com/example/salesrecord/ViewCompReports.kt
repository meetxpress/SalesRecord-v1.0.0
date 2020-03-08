package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ViewCompReports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comp_reports)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }
}
