package com.example.salesrecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if((LoginUsername.text.toString().trim() == "") and (LoginPassword.text.toString().trim() == "")){
                Toast.makeText(this,"Please Enter Username and Password to Login!",Toast.LENGTH_LONG).show()
            }
            else {
                if ((LoginUsername.text.toString().equals("admin")) and (LoginPassword.text.toString().equals("aadmin"))) {
                    var per=getSharedPreferences("myPref", Context.MODE_PRIVATE)
                    var edit=per.edit()
                    edit.putString("user",LoginUsername.text.toString())
                    edit.commit()
                    var i = Intent(this@LoginActivity, SuperAdminHome::class.java)
                    startActivity(i)
                    finish()
                }
                else if ((LoginUsername.text.toString().equals("comp")) and (LoginPassword.text.toString().equals("ccomp"))) {
                    var per=getSharedPreferences("myPref", Context.MODE_PRIVATE)
                    var edit=per.edit()
                    edit.putString("user",LoginUsername.text.toString())
                    edit.commit()
                    Toast.makeText(this, "Welcome " + LoginUsername.text, Toast.LENGTH_LONG).show()
                    var i = Intent(this@LoginActivity, DistributerHome::class.java)
                    startActivity(i)
                    finish()
                }
                else {
                    Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_LONG).show()
                    LoginPassword.setText("")
                }
            }
        }
    }
}
