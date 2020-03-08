package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DeactivateCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deactivate_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)


    }
}
