package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_company.*

class HomeCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnAddEmp.setOnClickListener {
            var i = Intent(this@HomeCompany, RegisterEmployee::class.java)
            startActivity(i)
        }

        btnMngEmp.setOnClickListener{
            var i = Intent(this@HomeCompany, ManageEmployee::class.java)
            startActivity(i)
        }

        btnViewAttendance.setOnClickListener{
            var i = Intent(this@HomeCompany, PunchAttendance::class.java)
            startActivity(i)
        }

        btnViewReport.setOnClickListener{
            Toast.makeText(this,"View Report Button!",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.exit_controlmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            var per=getSharedPreferences("myPref", Context.MODE_PRIVATE)
            var edit=per.edit()
            edit.clear()
            edit.commit()
            Toast.makeText(this,"Logged out Successfully!",Toast.LENGTH_LONG).show()
            var inte=Intent(this,LoginActivity::class.java)
            startActivity(inte)
            finish()
        }
        else{
            Toast.makeText(this,"Random Option!",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
