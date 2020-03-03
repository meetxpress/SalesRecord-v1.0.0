package com.example.salesrecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_company.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class RegisterCompany : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnRegisterCompany.setOnClickListener {
            if((CompName.toString().length <0) and
                (CompEmail.toString().length <0) and
                (CompCity.toString().length <0) and
                (CompPincode.toString().length <0) and
                (CompPhno1.toString().length <0) and
                (CompContactPerson.toString().length <0) and
                (CompLicNo.toString().length <0))
            {
                Toast.makeText(this@RegisterCompany,CompName.toString().length,Toast.LENGTH_LONG).show()
            }
            else {
                var coName=CompName.text.toString()
                var coEmail=CompEmail.text.toString()
                var coCity=CompCity.text.toString()
                var coPin=CompPincode.text.toString()
                var coPhno1=CompPhno1.text.toString()
                var coConPer=CompContactPerson.text.toString()
                var coLic=CompLicNo.text.toString()
                var coGST=CompGSTno.text.toString()
                var coWeb=CompWebsite.text.toString()
                callService(coName, coEmail, coCity, coPin, coPhno1, coConPer, coLic)
            }
        }
    }

    fun callService(coName:String, coEmail:String, coCity:String, coPin:String, coPhno1:String, coConPer:String, coLic:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("name",coName)
                .add("email",coEmail)
                .add("city",coCity)
                .add("pincode",coPin)
                .add("phno1",coPhno1)
                .add("contact_person",coConPer)
                .add("lic_no",coLic)
                //.add("get_no",coGST)
                //.add("website",coWeb)
                .build()

            var req=Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/addcomp.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check","abcde");
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var str=response.body!!.string()
                        Log.v("test",str)

                        val jsonObj = JSONObject(str)
                        val flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message")

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RegisterCompany,"Successful.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@RegisterCompany,SuperAdminHome::class.java)
                                startActivity(i)
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RegisterCompany,"Failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}