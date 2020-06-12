package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_employee.*
import kotlinx.android.synthetic.main.activity_manage_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HomeEmployee : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnViewProfile.setOnClickListener {
            var i = Intent(this@HomeEmployee, ManageEmployee::class.java)
            startActivity(i)
        }

        btnEmpAttendance.setOnClickListener {
            var i = Intent(this@HomeEmployee, PunchAttendance::class.java)
            startActivity(i)
        }

        btnSalary.setOnClickListener {
            var i = Intent(this@HomeEmployee, SalarySummaryActivity::class.java)
            startActivity(i)
        }

        btnRoutineTask.setOnClickListener{
            var i = Intent(this@HomeEmployee, RoutineTaskActivity::class.java)
            startActivity(i)
        }

        btnViewEmpReport.setOnClickListener{
            var i = Intent(this@HomeEmployee, ViewEmpReports::class.java)
            startActivity(i)
        }

        btnApplyLeave.setOnClickListener{
            var i = Intent(this@HomeEmployee, ApplyForLeave::class.java)
            startActivity(i)
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
            Toast.makeText(this@HomeEmployee,"Logged out Successfully!",Toast.LENGTH_LONG).show()
            var inte=Intent(this,LoginActivity::class.java)
            startActivity(inte)
            finish()
        }
        else{
            Toast.makeText(this@HomeEmployee,"Random Option!",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this@HomeEmployee.doubleBackToExitPressedOnce = true
        Toast.makeText(this@HomeEmployee, "Please BACK again to exit.", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 1000)
    }
}
