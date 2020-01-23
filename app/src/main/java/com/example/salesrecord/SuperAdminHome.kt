package com.example.salesrecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_super_admin_home.*

class SuperAdminHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_admin_home)

        btnAddComp.setOnClickListener {
            var i = Intent(this@SuperAdminHome, RegisterCompany::class.java)
            startActivity(i)
        }

        btnManageComp.setOnClickListener {
            var i = Intent(this@SuperAdminHome, ManageCompany::class.java)
            startActivity(i)
        }
    }
}
