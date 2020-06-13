package com.example.salesrecord

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_comp_password.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class changeCompPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_comp_password)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id=preference.getString("uname","Wrong").toString()

        btnChangeCompPassword.setOnClickListener{
            var cOldPass:String=compOldPass.text.toString()
            var cNewPass:String=compNewPass.text.toString()
            var cConfPass:String=compConfNewPass.text.toString()

            if((cOldPass == " ") && (cNewPass == " ") && (cConfPass == " ")){
                Toast.makeText(this@changeCompPassword, "Field(s) is(are) missing.",Toast.LENGTH_LONG).show()
            }else{
                if(cNewPass == cConfPass){
                    callService(comp_id, cOldPass, cNewPass, cConfPass)
                }else{
                    Toast.makeText(this@changeCompPassword, "Confirm password is not matching with new Password.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun callService(comp_id:String, cOldPass:String, cNewPass:String, cConfPass:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("comp_id",comp_id)
                .add("cOldPass",cOldPass)
                .add("cNewPass",cNewPass)
                .add("cConfPass",cConfPass)
                .build()

            var request = Request.Builder()
                .url("http://192.168.43.70/SalesRecord/changeCompPassword.php")
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
                                Toast.makeText(this@changeCompPassword, message, Toast.LENGTH_LONG).show()
                                var i= Intent(this@changeCompPassword, HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@changeCompPassword, message, Toast.LENGTH_LONG).show()
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
