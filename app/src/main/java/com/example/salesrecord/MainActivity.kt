package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.logo_splash)
        logo.startAnimation(animation)

        val animation1 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.logo_text)
        logo_text.startAnimation(animation1)

        Handler().postDelayed({
            var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            var str=preference.getString("uname","Wrong")
            if(str.equals("Wrong")){
                var i=Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else{
                var ss= str?.substring(0,1)
                if(str == "999999"){
                    var i=Intent(this@MainActivity,HomeSuperAdmin::class.java)
                    startActivity(i)
                    finish()
                }else if(ss == "1"){
                    var i=Intent(this@MainActivity,HomeCompany::class.java)
                    startActivity(i)
                    finish()
                }else if(ss == "3") {
                    var i = Intent(this@MainActivity, HomeEmployee::class.java)
                    startActivity(i)
                    finish()
                }else{
                    //Toast.makeText(this@MainActivity, "Verify your credential.", Toast.LENGTH_LONG).show()
                }
            }
        },1000)
    }
}
