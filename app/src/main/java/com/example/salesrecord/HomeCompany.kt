package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_company.*

class HomeCompany : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnMngComp.setOnClickListener{
            var i = Intent(this@HomeCompany, ManageCompany::class.java)
            startActivity(i)
        }

        btnAddEmp.setOnClickListener {
            var i = Intent(this@HomeCompany, RegisterEmployee::class.java)
            startActivity(i)
        }

        btnViewCompReport.setOnClickListener{
            var i = Intent(this@HomeCompany, ViewCompReports::class.java)
            startActivity(i)
        }

        btnDeleteEmp.setOnClickListener{
            var i = Intent(this@HomeCompany, DeactivateEmployee::class.java)
            startActivity(i)
        }

        btnAddShop.setOnClickListener{
            var i = Intent(this@HomeCompany, RegisterShop::class.java)
            startActivity(i)
        }

        btnAssignTarget.setOnClickListener{
            var i = Intent(this@HomeCompany, AssignTargetActivity::class.java)
            startActivity(i)
        }

        btnViewLeaves.setOnClickListener{
            var i = Intent(this@HomeCompany, ViewLeaves::class.java)
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

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this@HomeCompany.doubleBackToExitPressedOnce = true
        Toast.makeText(this@HomeCompany, "Please BACK again to exit.", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 1000)
    }
}
