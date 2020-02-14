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

        btnRegisterCompany.setOnClickListener {
            if((CompName.toString().length <0) and
                (CompEmail.toString().length <0) and
                (CompCity.toString().length <0) and
                (CompPincode.toString().length <0) and
                (CompPhno1.toString().length <0) and
                (CompContactPerson.toString().length <0) and
                (CompLicNo.toString().length <0))
            {
                Toast.makeText(this,CompName.toString().length,Toast.LENGTH_LONG).show()
            }
            else {
                var coName=CompName.text.toString()
                var coEmail=CompEmail.text.toString()
                var coCity=CompCity.text.toString()
                var coPin=CompPincode.text.toString()
                var coPhno1=CompPhno1.text.toString()
                var coPhno2=CompPhno2.text.toString()
                var coConPer=CompContactPerson.text.toString()
                var coLic=CompLicNo.text.toString()
                var coGST=CompGSTno.text.toString()
                var coWeb=CompWebsite.text.toString()
                callService(coName, coEmail, coCity, coPin, coPhno1, coConPer, coLic)

                //Toast.makeText(this,"LOL Else part",Toast.LENGTH_LONG).show()
                /*CompName.setText("")
                CompEmail.setText("")  
                CompCity.setText("")
                CompPincode.setText("")
                CompPhno.setText("")
                CompContactPerson.setText("")
                CompLicNo.setText("")
                CompGSTno.setText("")
                CompWebsite.setText("")*/
            }
        }
    }

    fun callService(coName:String, coEmail:String, coCity:String, coPin:String, coPhno1:String, coConPer:String, coLic:String){
        try{
            var temp:Int=0

            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("name",coName)
                .add("email",coEmail)
                .add("city",coCity)
                .add("pincode",coPin)
                .add("phno1",coPhno1)
                //.add("phno2",coPhno2)
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
                        temp=jsonObj.getInt("success")
                        Log.v("flagvalue",temp.toString())
                        Log.v("abc",response.toString())
                        Log.v("cmd",temp.toString())
                        var message=jsonObj.getString("message")
                        Log.v("key",response.toString())
                    }
                }
            })
            if(temp==1) {
                Log.v("lol",temp.toString())
                Toast.makeText(applicationContext,"Registered Successfully.",Toast.LENGTH_LONG).show()
                /*var i=Intent(this, SuperAdminHome::class.java)
                startActivity(i)
                finish()*/
            }else {
                Log.v("lmao",temp.toString())

                Toast.makeText(applicationContext,"Can not Register Company.",Toast.LENGTH_LONG).show()
                //var i= Intent(this, SuperAdminHome::class.java)
                //startActivity(i)
                //finish()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }


    }
}
