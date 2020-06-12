package com.example.salesrecord

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_register_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class RegisterEmployee : AppCompatActivity() {

    var eGen:String = " "
    var eDob:String = " "
    var shop_id:String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var comp_id = preference.getString("uname","Wrong").toString()

        var c=Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        EmpDoB.setOnClickListener {
            var dpd=DatePickerDialog(this@RegisterEmployee,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay->
                eDob=("$mYear-$mMonth-$mDay")
                EmpDoB.setText(eDob).toString()
            }, year, month, day)
            dpd.show()
            //Toast.makeText(this@RegisterEmployee, eDob.toString(), Toast.LENGTH_LONG).show()
        }

        btnSearchShopId.setOnClickListener{
            shop_id=searchShopId.text.toString()
            callSearchsShopService(shop_id, comp_id)
        }

        btnRegisterEmp.setOnClickListener {
            if((EmpName.toString().length <0) and
                (EmpPassword.toString().length <0) and
                (EmpDoB.toString().length <0) and
                (EmpAadharNo.toString().length <0) and
                (EmpPhno1.toString().length <0) and
                (EmpPhno2.toString().length <0) and
                (EmpEmail.toString().length <0) and
                (EmpAdd.toString().length <0) and
                (EmpPincode.toString().length <0) and
                (EmpCity.toString().length <0) and
                (EmpState.toString().length <0) and
                (EmpStatus.toString().length <0) and
                (shop_id.length <0))
            {
                Toast.makeText(this@RegisterEmployee,"Required Fields are missing.",Toast.LENGTH_LONG).show()
            } else {
                var eName=EmpName.text.toString()
                var ePass=EmpPassword.text.toString()
                eDob=EmpDoB.text.toString()
                var eAadhar=EmpAadharNo.text.toString()
                var ePhno1=EmpPhno1.text.toString()
                var ePhno2=EmpPhno2.text.toString()
                var eMail=EmpEmail.text.toString()
                var eAdd=EmpAdd.text.toString()
                var ePincode=EmpPincode.text.toString()
                var eCity=EmpCity.text.toString()
                var eState=EmpState.text.toString()
                var eStatus=EmpStatus.selectedItem.toString()

                callService(comp_id, eName, ePass, eDob, eGen, eAadhar, ePhno1, ePhno2, eMail, eAdd, ePincode, eCity, eState, eStatus, shop_id)
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rb_male ->
                    if (checked) {
                        eGen=rb_male.text.substring(0,1)
                    }
                R.id.rb_female ->
                    if (checked) {
                        eGen=rb_female.text.substring(0,1)
                    }

                R.id.rb_transgender ->
                    if (checked) {
                        eGen=rb_transgender.text.substring(0,1)
                    }
            }
        }
        Toast.makeText(this@RegisterEmployee, eGen, Toast.LENGTH_SHORT).show()
    }

    fun callSearchsShopService(shop_id:String, comp_id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("shop_id",shop_id)
                .add("comp_id",comp_id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/btnSearchShop.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        var str=response.body!!.string()
                        Log.v("str",str)
                        var js= JSONObject(str)
                        var flag=js.getInt("success")
                        var msg=js.getString("message")

                        if(flag == 1){
                            Log.v("fs1", flag.toString())
                            runOnUiThread{
                                //Toast.makeText(this@DeactivateEmployee,"Data Found", Toast.LENGTH_LONG).show()

                                var shop_name=js.getString("shop_name")
                                Log.v("res1",str)

                                val builder = AlertDialog.Builder(this@RegisterEmployee)
                                builder.setTitle("Shop Details")
                                builder.setMessage("Confirm the details of the shop you searched." +
                                        "\nShop Id: $shop_id " +
                                        "\nShop Name.: $shop_name")

                                builder.setPositiveButton("Confirm") { _, _ -> }

                                builder.setNegativeButton("Reset") {_, _ ->
                                    searchShopId.setText(" ")
                                    searchShopId.hint = " Search Shop ID*"
                                }
                                builder.show()
                            }
                        }else{
                            Log.v("ff1", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@RegisterEmployee,"No Shop found.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun callService(comp_id:String, eName:String, ePass:String, eDob:String, eGen:String, eAadhar:String, ePhno1:String, ePhno2:String, eMail:String, eAdd:String, ePincode:String, eCity:String, eState:String, eStatus:String, shop_id:String){
        try{
            var client=OkHttpClient()

            var formBody=FormBody.Builder()
                .add("comp_id",comp_id)
                .add("eName",eName)
                .add("ePass",ePass)
                .add("eGen",eGen)
                .add("eDob",eDob)
                .add("eAadhar",eAadhar)
                .add("ePhno1",ePhno1)
                .add("eMail",eMail)
                .add("eAdd",eAdd)
                .add("ePincode",ePincode)
                .add("eCity",eCity)
                .add("eState",eState)
                .add("eStatus",eStatus)
                .add("shop_id",shop_id)
                .build()

                /*Log.v("comp_id",comp_id)
                Log.v("eName",eName)
                Log.v("ePass",ePass)
                Log.v("eGen",eGen)
                Log.v("eDob",eDob)
                Log.v("eAadhar",eAadhar)
                Log.v("ePhno1",ePhno1)
                Log.v("eMail",eMail)
                Log.v("eAdd",eAdd)
                Log.v("ePincode",ePincode)
                Log.v("eCity",eCity)
                Log.v("eState",eState)
                Log.v("eStatus",eStatus)*/

            var request=Request.Builder()
                .url("http://192.168.43.215/SalesRecord/addEmployee.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        Log.v("check","abcde")
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
                                Toast.makeText(this@RegisterEmployee, message, Toast.LENGTH_LONG).show()
                                var i= Intent(this@RegisterEmployee,HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@RegisterEmployee, message, Toast.LENGTH_LONG).show()
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
