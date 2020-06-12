package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_super_admin.*

class HomeSuperAdmin : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_super_admin)

        btnAddComp.setOnClickListener {
            var i = Intent(this@HomeSuperAdmin, RegisterCompany::class.java)
            startActivity(i)
        }

        btnDeleteComp.setOnClickListener {
            var i = Intent(this@HomeSuperAdmin, DeactivateCompany::class.java)
            startActivity(i)
        }

        btnViewSuperAdminReport.setOnClickListener{
            startActivity(Intent(this@HomeSuperAdmin, ViewSuperAdminReport::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_controlmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            var per=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            var edit=per.edit()
            edit.clear()
            edit.commit()
            Toast.makeText(this,"Logged out Successfully!",Toast.LENGTH_LONG).show()
            var inte=Intent(this@HomeSuperAdmin,LoginActivity::class.java)
            startActivity(inte)
            finish()
        }
        else{
            Toast.makeText(this,"Random Option!",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this@HomeSuperAdmin.doubleBackToExitPressedOnce = true
        Toast.makeText(this@HomeSuperAdmin, "Please BACK again to exit.", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 1000)
    }
}
