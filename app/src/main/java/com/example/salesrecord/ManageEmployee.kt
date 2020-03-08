package com.example.salesrecord

import android.app.DatePickerDialog
import android.icu.text.UnicodeSetSpanner
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class ManageEmployee : AppCompatActivity() {

    var emp_gen:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnUpdateEmp.setOnClickListener {
            var emp_id=etUpdateEmp.text.toString()
            var emp_name=UpEmpName.text.toString()
            RadioBtnGroup.setOnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                emp_gen=radio.text.substring(0).toString()
                Toast.makeText(this@ManageEmployee, "${radio.text}", Toast.LENGTH_SHORT).show()
            }
            var emp_dob=UpEmpDoB.text.toString()
            var emp_aadhar=UpEmpAadharNo.text.toString()
            var emp_phno1 =UpEmpPhno1.text.toString()
            var emp_email=UpEmpEmail.text.toString()
            var emp_add=UpEmpAdd.text.toString()            
            var emp_city=UpEmpCity.text.toString()
            var emp_pincode=UpEmpPincode.text.toString()
            var emp_state=UpEmpState.text.toString()
            var emp_deg =UpEmpDeg.text.toString()
            callServiceUpdate(emp_id, emp_name, emp_add, emp_gen, emp_dob, emp_aadhar, emp_phno1, emp_email, emp_city, emp_pincode, emp_state, emp_deg)
            //Toast.makeText(this,"Company details updated Successfully",Toast.LENGTH_LONG).show()
        }

        btnDeleteEmp.setOnClickListener {
            Toast.makeText(this@ManageEmployee,"Employee Deleted Successfully.",Toast.LENGTH_LONG).show()
            nulling()
        }

        btnSearchEmp.setOnClickListener {
            var id = etUpdateEmp.text.toString();
            callService(id)
        }
    }

    fun updateDoB(view: View) {
        var dpd: DatePickerDialog
        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)
        val date=findViewById<EditText>(R.id.EmpDoB)
        var d: Date
        dpd= DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener {
                view,year,month,day->date.setText(" $day / ${month+1} / $year")
        },year,month,day)
        //if(dpd > c.get(Calendar.DATE)) {
        dpd.show()
        //}
    }

    fun nulling(){
        etUpdateEmp.setText("")
        UpEmpName.setText("")
        UpEmpDoB.setText("")
        UpEmpAadharNo.setText("")
        UpEmpPhno1.setText("")
        UpEmpEmail.setText("")
        UpEmpAdd.setText("")
        UpEmpCity.setText("")
        UpEmpPincode.setText("")
        UpEmpState.setText("")
        UpEmpDeg.setText("")
    }

    fun callService(id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", id)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/updateEmpbtn.php")
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

                        //filling data in EditText
                        var eName=js.getString("emp_name")
                        var eGen=js.getString("emp_gen")
                        var eDob = js.getString("emp_dob")
                        var eAadhar = js.getString("emp_aadharno")
                        var ePhno1 = js.getString("emp_mob1")
                        var eMail = js.getString("emp_email")
                        var eAdd = js.getString("emp_address")
                        var eCity = js.getString("emp_city")
                        var ePincode = js.getString("emp_pincode")
                        var eState = js.getString("emp_state")
                        var eDeg = js.getString("emp_deg")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageEmployee,"Data Found", Toast.LENGTH_LONG).show()

                                var emp_name=UpEmpName.text.toString()
                                RadioBtnGroup.setOnCheckedChangeListener { group, checkedId ->
                                    val radio: RadioButton = findViewById(checkedId)
                                    var emp_gen = radio.text
                                    Toast.makeText(this@ManageEmployee,"${radio.text}",Toast.LENGTH_SHORT).show()
                                }

                                UpEmpName.setText(eName).toString()
                                UpEmpDoB.setText(eDob).toString()
                                UpEmpAadharNo.setText(eAadhar).toString()
                                UpEmpPhno1.setText(ePhno1).toString()
                                UpEmpEmail.setText(eMail).toString()
                                UpEmpAdd.setText(eAdd).toString()
                                UpEmpCity.setText(eCity).toString()
                                UpEmpPincode.setText(ePincode).toString()
                                UpEmpState.setText(eState).toString()
                                UpEmpDeg.setText(eDeg).toString()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageEmployee,"No data found", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun callServiceUpdate(emp_id:String, emp_name:String, emp_add:String, emp_gen:String, emp_dob:String, emp_aadhar:String, emp_phno1:String, emp_email:String, emp_city:String, emp_pincode:String, emp_state:String,  emp_deg:String){
    try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("id", emp_id)
                .add("name", emp_name)
                .add("gender",emp_gen)
                .add("dob", emp_dob)
                .add("aadhar", emp_aadhar)
                .add("phno1", emp_phno1)
                .add("email", emp_email)
                .add("add", emp_add)
                .add("city", emp_city)
                .add("pincode", emp_pincode)
                .add("state", emp_state)
                .add("deg", emp_deg)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/updateEmp.php")
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
                                Toast.makeText(this@ManageEmployee,"Updated Successfully!", Toast.LENGTH_LONG).show()
                                nulling()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageEmployee,"An Error occured!", Toast.LENGTH_LONG).show()
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
