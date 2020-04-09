package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlin.system.exitProcess

class forgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exitApp.setOnClickListener{
            //exitProcess(-1)
            finishAffinity()
        }
    }
}
