package com.example.salesrecord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.style.TabStopSpan
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.system.exitProcess
import androidx.core.view.isVisible as isVisible

class forgotPassword : AppCompatActivity() {
    var btnFlag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        //back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var for_id = " "
        var for_refNo = " "
        var for_pass = " "
        var for_conPass = " "

        //enabling references EditText
        forgotID.setOnFocusChangeListener{ _, _ ->
            forgotReference.setText("")
            forgotReference.hint = " Enter Valid Reference*"
            forgotReference.isEnabled = true
            forgotReference.isClickable = true
        }

        //changing Hint according to UserID
        forgotReference.setOnFocusChangeListener{ _, _ ->
            for_id=forgotID.text.toString()

            var ss= for_id.substring(0, 1)
            if(ss == "1"){
                forgotReference.hint = "Enter Company Licence Number*"
            }else if(ss == "3") {
                forgotReference.hint = "Enter Aadhar card Number*"
            }else{
                forgotReference.hint = "Verify your credential."
                forgotReference.isEnabled = false
                forgotReference.isClickable = false
            }
        }

        //making visible the new password tabs
        forgotNewPassword.setOnFocusChangeListener { _, _ ->
            forgotConfirmPassword.visibility = View.VISIBLE
            forgotConfirmPassword.isEnabled = true
            forgotConfirmPassword.isClickable = true
        }

        //calling services according to the flag value.
        btnForgotSearch.setOnClickListener{
            for_refNo = forgotReference.text.toString()
            for_pass = forgotNewPassword.text.toString()
            for_conPass = forgotNewPassword.text.toString()
            if(btnFlag == 0){
                if((for_id == " ") && (for_refNo == " ")){
                    Toast.makeText(this@forgotPassword, "Required Fields are missing.", Toast.LENGTH_LONG).show()
                }else {
                    Log.v("for_id", for_id)
                    Log.v("for_refNo", for_refNo)
                    callService(for_id, for_refNo)
                }
            } else if(btnFlag == 1){
                if((for_id == " ") && (for_pass == " ") && (for_conPass == " ")){
                    Toast.makeText(this@forgotPassword, "Required Fields are missing.", Toast.LENGTH_LONG).show()
                }else {
                    if (for_pass == for_conPass) {
                        callMainservice(for_id, for_pass)
                    } else {
                        Toast.makeText(this@forgotPassword,"Password is not matching with first password.",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //fetching the data of user
    fun callService(for_id:String, for_refNo:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("for_id", for_id)
                .add("for_refNo", for_refNo)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/fetchUserDetails.php")
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
                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@forgotPassword,"Enter New Password.", Toast.LENGTH_LONG).show()
                                forgotNewPassword.isEnabled = true
                                forgotNewPassword.isClickable = true
                                forgotNewPassword.visibility = View.VISIBLE;

                                forgotConfirmPassword.isEnabled = true
                                forgotConfirmPassword.isClickable = true

                                btnForgotSearch.setText("Reset Password")
                                btnFlag++
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@forgotPassword,"Invalid Information.!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    //setting the new password of user
    fun callMainservice(for_id:String, for_pass:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("for_id", for_id)
                .add("for_pass", for_pass)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/setUserDetails.php")
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
                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@forgotPassword,"Password Reset.", Toast.LENGTH_LONG).show()

                                forgotNewPassword.visibility = View.VISIBLE;
                                forgotNewPassword.isEnabled = true
                                forgotNewPassword.isClickable = true

                                btnForgotSearch.text = "Reset Password"
                                btnFlag++
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@forgotPassword,"Something went wrong.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
        btnFlag = 0
    }

}