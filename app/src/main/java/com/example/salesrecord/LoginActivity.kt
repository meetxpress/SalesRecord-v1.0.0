package com.example.salesrecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    var flag:String = ""
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
                callSevice(user,pass)
            }
        }
    }

    fun callSevice(user:String, pass:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("username",user)
                .add("password",pass)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/login.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        var js= JSONObject(str)
                        var flag=js.getInt("success")
                        var msg=js.getString("message")

                        Log.v("res",str)
                        Log.v("cd", flag.toString())
                        Log.v("ms",msg)

                        if(flag == 1){
                            Log.v("f1", flag.toString())
                            runOnUiThread{
                                var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                                var editor=preference.edit()
                                editor.putString("uname",LoginUsername.text.toString());
                                editor.commit()
                                //Toast.makeText(this@LoginActivity,LoginUsername.text.toString(), Toast.LENGTH_LONG).show()

                                Toast.makeText(this@LoginActivity,"Logged In Successfully.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@LoginActivity,HomeSuperAdmin::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else if(flag == 2){
                            Log.v("f2", flag.toString())
                            runOnUiThread{
                                var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                                var editor=preference.edit()
                                editor.putString("uname",LoginUsername.text.toString());
                                editor.commit()
                                Toast.makeText(this@LoginActivity,LoginUsername.text.toString(), Toast.LENGTH_LONG).show()

                                Toast.makeText(this@LoginActivity,"Logged In Successfully.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@LoginActivity,HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else if(flag == 3){
                            Log.v("f3", flag.toString())
                            runOnUiThread{
                                var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                                var editor=preference.edit()
                                editor.putString("uname",LoginUsername.text.toString());
                                editor.commit()

                                Toast.makeText(this@LoginActivity,"Logged In Successfully.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@LoginActivity,HomeEmployee::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@LoginActivity,"Login Failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

}