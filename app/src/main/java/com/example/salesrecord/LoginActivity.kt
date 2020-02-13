package com.example.salesrecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if((LoginUsername.text.toString().trim() == "") and (LoginPassword.text.toString().trim() == "")){
                Toast.makeText(this,"Please Enter Username and Password to Login!",Toast.LENGTH_LONG).show()
            }
            else {
                var user=LoginUsername.text.toString()
                var pass=LoginPassword.text.toString()
                Toast.makeText(this,"Toast Button",Toast.LENGTH_LONG).show()
                callSevice(user,pass)
            }
        }
    }

    fun callSevice(user:String, pass:String){
        Toast.makeText(this,"Service toast",Toast.LENGTH_LONG).show()
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("username",user)
                .add("password",pass)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2/SalesRecord/login.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.e("test",str)

                        val jsonObj = JSONObject(str)
                        var flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message")

                        if(flag==1)
                        {
                            Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                            finish();
                        }
                        else
                        {
                            Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }
}
