package com.example.salesrecord

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.UnicodeSetSpanner
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.sql.Date
import java.util.*

class ManageEmployee : AppCompatActivity() {

    fun nulling(){
        UpEmpName.setText("")
        UpEmpDoB.setText("")
        UpEmpAadharNo.setText("")
        UpEmpPhno1.setText("")
        UpEmpEmail.setText("")
        UpEmpAdd.setText("")
        UpEmpCity.setText("")
        UpEmpPincode.setText("")
        UpEmpState.setText("")
    }
    fun enableEdits(){
        UpEmpName.isEnabled=true
        UpEmpDoB.isEnabled=true
        UpEmpAadharNo.isEnabled=true
        UpEmpPhno1.isEnabled=true
        UpEmpEmail.isEnabled=true
        UpEmpAdd.isEnabled=true
        UpEmpCity.isEnabled=true
        UpEmpPincode.isEnabled=true
        UpEmpState.isEnabled=true
    }
    fun disableEdits(){
        UpEmpName.isEnabled=false
        UpEmpDoB.isEnabled=false
        UpEmpAadharNo.isEnabled=false
        UpEmpPhno1.isEnabled=false
        UpEmpEmail.isEnabled=false
        UpEmpAdd.isEnabled=false
        UpEmpCity.isEnabled=false
        UpEmpPincode.isEnabled=false
        UpEmpState.isEnabled=false
    }

    var emp_gen:String = ""
    var eDob:String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        var preference=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var emp_id = preference.getString("uname","Wrong").toString()
        callService(emp_id)

        disableEdits()
        var c=Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        UpEmpDoB.setOnClickListener {
            var dpd=DatePickerDialog(this@ManageEmployee,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay->
                eDob=("$mYear-$mMonth-$mDay")
                UpEmpDoB.setText(eDob).toString()
            }, year, month, day)
            dpd.show()
        }

        btnUpdateEmp.setOnClickListener {
            var emp_name=UpEmpName.text.toString()
            var emp_dob: String =UpEmpDoB.text.toString()
            var emp_aadhar=UpEmpAadharNo.text.toString()
            var emp_phno1 =UpEmpPhno1.text.toString()
            var emp_email=UpEmpEmail.text.toString()
            var emp_add=UpEmpAdd.text.toString()            
            var emp_city=UpEmpCity.text.toString()
            var emp_pincode=UpEmpPincode.text.toString()
            var emp_state=UpEmpState.text.toString()
            callServiceUpdate(emp_id, emp_name, emp_add, emp_gen, emp_dob, emp_aadhar, emp_phno1, emp_email, emp_city, emp_pincode, emp_state)
        }
    }

    fun callService(emp_id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id", emp_id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/updateEmpbtn.php")
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
                        emp_gen=js.getString("emp_gen")
                        var eDob = js.getString("emp_dob")
                        var eAadhar = js.getString("emp_aadharno")
                        var ePhno1 = js.getString("emp_mob1")
                        var eMail = js.getString("emp_email")
                        var eAdd = js.getString("emp_address")
                        var ePincode = js.getString("emp_pincode")
                        var eCity = js.getString("emp_city")
                        var eState = js.getString("emp_state")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageEmployee,"Data Found", Toast.LENGTH_LONG).show()

                                UpEmpName.setText(eName).toString()
                                UpEmpDoB.setText(eDob).toString()
                                UpEmpAadharNo.setText(eAadhar).toString()
                                UpEmpPhno1.setText(ePhno1).toString()
                                UpEmpEmail.setText(eMail).toString()
                                UpEmpAdd.setText(eAdd).toString()
                                UpEmpCity.setText(eCity).toString()
                                UpEmpPincode.setText(ePincode).toString()
                                UpEmpState.setText(eState).toString()
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

    fun callServiceUpdate(emp_id:String, emp_name:String, emp_add:String, emp_gen:String, emp_dob:String, emp_aadhar:String, emp_phno1:String, emp_email:String, emp_city:String, emp_pincode:String, emp_state:String){
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
                .build()

            //Log.v("edit", emp_gen + emp_dob )

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/updateEmp.php")
                .post(formBody)
                .build()

            client.newCall(req).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception",e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        Log.v("check","abcde")
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
                                Toast.makeText(this@ManageEmployee,"An Error occurred!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.up_rb_male ->
                    if (checked) {
                        emp_gen=up_rb_male.text.substring(0,1)
                    }
                R.id.up_rb_female ->
                    if (checked) {
                        emp_gen=up_rb_female.text.substring(0,1)
                    }

                R.id.up_rb_transgender ->
                    if (checked) {
                        emp_gen=up_rb_transgender.text.substring(0,1)
                    }
            }
        }
        //Toast.makeText(this@ManageEmployee, emp_gen, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editable,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.edit){
            Toast.makeText(this@ManageEmployee,"Enabled Editing",Toast.LENGTH_SHORT).show()
            enableEdits()
        }

        if(item.itemId == R.id.changePass){
            var i= Intent(this@ManageEmployee, changeEmpPassword::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
}
