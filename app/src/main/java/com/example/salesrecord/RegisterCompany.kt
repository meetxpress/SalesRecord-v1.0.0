package com.example.salesrecord

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
                (CompPhno.toString().length <0) and
                (CompContactPerson.toString().length <0) and
                (CompLicNo.toString().length <0) and
                (CompGSTno.toString().length <0) and
                (CompWebsite.toString().length <0))
            {
               // Toast.makeText(this,CompName.toString().length,Toast.LENGTH_LONG).show()
            }
            else {
                var coName=CompName.text.toString()
                var coEmail=CompEmail.text.toString()
                var coCity=CompCity.text.toString()
                var coPin=CompPincode.text.toString()
                var coPhno=CompPhno.text.toString()
                var coConPer=CompContactPerson.text.toString()
                var coLic=CompLicNo.text.toString()
                var coGST=CompGSTno.text.toString()
                var coWeb=CompWebsite.text.toString()
                callService(coName, coEmail, coCity, coPin, coPhno, coConPer, coLic, coGST, coWeb)

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

    fun callService(coName:String, coEmail:String, coCity:String, coPin:String, coPhno:String, coConPer:String, coLic:String, coGST:String, coWeb:String){
        try{
            Toast.makeText(this,"calling service part",Toast.LENGTH_LONG).show()
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("name",coName)
                .add("email",coEmail)
                .add("city",coCity)
                .add("pincode",coPin)
                .add("phno",coPhno)
                .add("contact_person",coConPer)
                .add("lic_no",coLic)
                .add("get_no",coGST)
                .add("website",coWeb)
                .build()

            var req=Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/insertUser.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        var str=response.body!!.string()
                        var js=JSONObject(str)
                       // var =js.getInt("success")
                        var message=js.getString("message")
                       // Log.e("id",id.toString())
                        Log.e("test",message.toString())

                    }
                }
            })
        }catch(e:Exception){
            e.printStackTrace()
        }
    }
}
