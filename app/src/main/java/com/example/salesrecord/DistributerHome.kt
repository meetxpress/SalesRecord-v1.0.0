package com.example.salesrecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_distributer_home.*

class DistributerHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distributer_home)
        btnAddEmp.setOnClickListener {
            Toast.makeText(this,"Add Employee Button!",Toast.LENGTH_LONG).show()
            var i = Intent(this@DistributerHome, RegisterEmployee::class.java)
            startActivity(i)
        }

        btnMngEmp.setOnClickListener{
            Toast.makeText(this,"Manage Employee Button!",Toast.LENGTH_LONG).show()
        }

        btnViewAttendance.setOnClickListener{
            Toast.makeText(this,"View Attendance Button!",Toast.LENGTH_LONG).show()
        }

        btnViewReport.setOnClickListener{
            Toast.makeText(this,"View Report Button!",Toast.LENGTH_LONG).show()
        }
    }
}
