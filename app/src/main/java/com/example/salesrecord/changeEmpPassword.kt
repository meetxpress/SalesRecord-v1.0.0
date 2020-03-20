package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_emp_password.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class changeEmpPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_emp_password)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()

        btnChangeEmpPassword.setOnClickListener{
            var eOldPass:String=empOldPass.text.toString()
            var eNewPass:String=empNewPass.text.toString()
            var eConfPass:String=empConfNewPass.text.toString()

            if((eOldPass == " ") && (eNewPass == " ") && (eConfPass == " ")){
                Toast.makeText(this@changeEmpPassword, "Field(s) is(are) missing.",Toast.LENGTH_LONG).show()
            }else{
                if(eNewPass == eConfPass){
                    callService(emp_id, eOldPass, eNewPass, eConfPass)
                }else{
                    Toast.makeText(this@changeEmpPassword, "Confirm password is not matching with new Password.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun callService(emp_id:String, eOldPass:String, eNewPass:String, eConfPass:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",emp_id)
                .add("eOldPass",eOldPass)
                .add("eNewPass",eNewPass)
                .add("eConfPass",eConfPass)
                .build()

            var request = Request.Builder()
                .url("http://192.168.43.231/SalesRecord/changeEmpPassword.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        Log.v("check","abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.v("test",str)

                        var jsonObj = JSONObject(str)
                        val flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message")

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)

                        if(flag == 1){
                            runOnUiThread {
                                Log.v("fs", flag.toString())
                                Toast.makeText(this@changeEmpPassword, message, Toast.LENGTH_LONG).show()

                                var i= Intent(this@changeEmpPassword, HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@changeEmpPassword, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}
