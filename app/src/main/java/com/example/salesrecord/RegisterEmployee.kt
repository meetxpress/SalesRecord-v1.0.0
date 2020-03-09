package com.example.salesrecord

import android.app.DatePickerDialog
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

    var eGen:String=""
    var eDob:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var c=Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        EmpDoB.setOnClickListener {
            var dpd=DatePickerDialog(this@RegisterEmployee,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay->
                eDob=("$mYear-$mMonth-$mDay")
                EmpDoB.setText(eDob)
            }, year, month, day)
            dpd.show()
            //Toast.makeText(this@RegisterEmployee, eDob.toString(), Toast.LENGTH_LONG).show()
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
                (EmpStatus.toString().length <0))
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

                RadioBtnGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    eGen = radio.text.substring(0,1)
                   // Toast.makeText(this@RegisterEmployee, eGen, Toast.LENGTH_SHORT).show()
                }
                callService(eName, ePass, eDob, eGen, eAadhar, ePhno1, ePhno2, eMail, eAdd, ePincode, eCity, eState, eStatus)
            }
        }
    }


    fun callService(eName:String, ePass:String, eDob:String, eGen:String, eAadhar:String, ePhno1:String, ePhno2:String, eMail:String, eAdd:String, ePincode:String, eCity:String, eState:String, eStatus:String){
        try{
            var client=OkHttpClient()

            var formBody=FormBody.Builder()
                .add("eName",eName)
                .add("ePass",ePass)
                .add("eGen",eGen)
                .add("eDob",eDob)
                .add("eAadhar",eAadhar)
                .add("ePhno1",ePhno1)
                //.add("ePhno2",ePhno2)
                .add("eMail",eMail)
                .add("eAdd",eAdd)
                .add("ePincode",ePincode)
                .add("eCity",eCity)
                .add("eState",eState)
                .add("eStatus",eStatus)
                .build()

            var request=Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/addEmployee.php")
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback{
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
                                Toast.makeText(this@RegisterEmployee,"Successful.!", Toast.LENGTH_LONG).show()
                                var i= Intent(this@RegisterEmployee,HomeCompany::class.java)
                                startActivity(i)
                                finish()
                            }
                        }else{
                            runOnUiThread {
                                Log.v("ff", flag.toString())
                                Toast.makeText(this@RegisterEmployee,"Failed", Toast.LENGTH_LONG).show()
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
