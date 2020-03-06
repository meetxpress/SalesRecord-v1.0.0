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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var gen=findViewById<RadioGroup>(R.id.RadioBtnGroup)
        var gender=findViewById<RadioButton>(gen.checkedRadioButtonId).text.toString()

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
                (EmpDeg.toString().length <0))
            {
                Toast.makeText(this@RegisterEmployee,"Required Fields are missing.",Toast.LENGTH_LONG).show()
            } else {
                var eName=EmpName.text.toString()
                var ePass=EmpPassword.text.toString()
                var eDob=EmpDoB.text.toString()
                var eAadhar=EmpAadharNo.text.toString()
                var ePhno1=EmpPhno1.text.toString()
                var ePhno2=EmpPhno2.text.toString()
                var eMail=EmpEmail.text.toString()
                var eAdd=EmpAdd.text.toString()
                var ePincode=EmpPincode.text.toString()
                var eCity=EmpCity.text.toString()
                var eState=EmpState.text.toString()
                var eStatus=EmpStatus.text.toString()
                var eDeg=EmpDeg.text.toString()

                RadioBtnGroup.setOnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = findViewById(checkedId)
                    var eGen=radio.text
                    Toast.makeText(this@RegisterEmployee, "${radio.text}", Toast.LENGTH_SHORT).show()
                }

                callService(eName, ePass, eDob, eAadhar, ePhno1, ePhno2, eMail, eAdd, ePincode, eCity, eState, eStatus, eDeg)
            }
        }
    }

    fun callService(eName:String, ePass:String, eDob:String, eAadhar:String, ePhno1:String, ePhno2:String, eMail:String, eAdd:String, ePincode:String, eCity:String, eState:String, eStatus:String, eDeg:String){
        try{
            var client=OkHttpClient()

            var formBody=FormBody.Builder()
                .add("ename",eName)
                .add("ePass",ePass)
               // .add("eGen",eGen)
                .add("eDob",eDob)
                .add("eAadhar",eAadhar)
                .add("ePhno1",ePhno1)
                .add("ePhno2",ePhno2)
                .add("eMail",eMail)
                .add("eAdd",eAdd)
                .add("ePincode",ePincode)
                .add("eCity",eCity)
                .add("eState",eState)
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

                        val jsonObj = JSONObject(str)
                        val flag=jsonObj.getInt("success")
                        var message=jsonObj.getString("message")

                        Log.v("res",response.toString())
                        Log.v("cdm",flag.toString())
                        Log.v("msg",message)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            Toast.makeText(this@RegisterEmployee,"Successful.!", Toast.LENGTH_LONG).show()
                            var i= Intent(this@RegisterEmployee,HomeCompany::class.java)
                            startActivity(i)
                            finish()
                        }else{
                            Log.v("ff", flag.toString())
                            Toast.makeText(this@RegisterEmployee,"Failed", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun addDoB(view:View) {
        var dpd:DatePickerDialog
        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)
        val date =findViewById<EditText>(R.id.EmpDoB)
        var d:Date

        dpd=DatePickerDialog(this@RegisterEmployee,R.style.DialogTheme, DatePickerDialog.OnDateSetListener {
                view,year,month,day->date.setText(" $day / ${month+1} / $year")
        },year,month,day)
    }

    fun onRadioButtonClicked(view: View) {
        var checked = view as RadioButton
        if (rb_male == checked) {
            Toast.makeText(this@RegisterEmployee, (rb_male.text.toString() + if (rb_male.isChecked) " Checked " else " UnChecked "), Toast.LENGTH_LONG).show()
        }
        if (rb_female == checked) {
            Toast.makeText(this@RegisterEmployee, (rb_female.text.toString() + if (rb_female.isChecked) " Checked " else " UnChecked "), Toast.LENGTH_LONG).show()
        }
        if(rb_transgender == checked){
            Toast.makeText(this@RegisterEmployee,(rb_transgender.text.toString() + if(rb_transgender.isChecked) "Checked" else "Unchecked"), Toast.LENGTH_LONG).show()
        }
    }
}
