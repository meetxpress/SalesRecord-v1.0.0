package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            var per=getSharedPreferences("myPref", Context.MODE_PRIVATE)
            var str=per.getString("user","Wrong")
            if(str.equals("Wrong")){
                var i=Intent(this@MainActivity,LoginActivity::class.java)
                startActivity(i)
                finish()
            }
            else{
                if(str.equals("admin")) {
                    var i=Intent(this@MainActivity,HomeSuperAdmin::class.java)
                    startActivity(i)
                    finish()
                }else if(str.equals("")){
                    var i=Intent(this@MainActivity,HomeSuperAdmin::class.java)
                    startActivity(i)
                    finish()
                }else{
                    var i=Intent(this@MainActivity,HomeEmployee::class.java)
                    startActivity(i)
                    finish()
                }
            }
        },1000)
    }
}
