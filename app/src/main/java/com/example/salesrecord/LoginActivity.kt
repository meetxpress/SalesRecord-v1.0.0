package com.example.salesrecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener {
            if((LoginUsername.text.toString().equals("admin")) and (LoginPassword.text.toString().equals("aadmin"))) {
                Toast.makeText(this, "Welcome " + LoginUsername.text, Toast.LENGTH_LONG).show()
                var i = Intent(this@LoginActivity, SuperAdminHome::class.java)
                startActivity(i)
            }
            else if((LoginUsername.text.toString().equals("comp")) and (LoginPassword.text.toString().equals("ccomp"))) {
                Toast.makeText(this, "Welcome " + LoginUsername.text, Toast.LENGTH_LONG).show()
                var i = Intent(this@LoginActivity, DistributerHome::class.java)
                startActivity(i)
            }
            else {
                Toast.makeText(this,"Invalid Username and Password",Toast.LENGTH_LONG).show()
                LoginPassword.text==null
            }
        }
    }
}
