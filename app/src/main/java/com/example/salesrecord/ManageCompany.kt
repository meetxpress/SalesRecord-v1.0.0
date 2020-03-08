package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_company.*
import kotlinx.android.synthetic.main.activity_register_company.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ManageCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnUpdateComp.setOnClickListener {
            var comp_id=etUpdateComp.text.toString()
            var comp_name=UpCompName.text.toString()
            var comp_email=UpCompEmail.text.toString()
            var comp_city= UpCompCity.text.toString()
            var comp_pincode=UpCompPincode.text.toString()
            var comp_phno=UpCompPhno.text.toString()
            var comp_person=UpCompContactPerson.text.toString()
            var comp_licno= UpCompLicNo.text.toString()
            var comp_gstno= UpCompGSTno.text.toString()
            var comp_website=UpCompWebsite.text.toString()

            callServiceUpdate(comp_id,comp_name,comp_email,comp_city,comp_pincode,comp_phno,comp_person,comp_licno,comp_gstno,comp_website)
            //Toast.makeText(this,"Company details updated Successfully",Toast.LENGTH_LONG).show()
        }

        /*
        btnDeleteComp.setOnClickListener {
            Toast.makeText(this@ManageCompany,"Company Deactivated Successfully.",Toast.LENGTH_LONG).show()
            nulling()
        }
        */

        btnSearchComp.setOnClickListener {
            var id = etUpdateComp.text.toString();
            callService(id)
        }
    }

    fun nulling(){
        UpCompName.setText("")
        UpCompEmail.setText("")
        UpCompCity.setText("")
        UpCompPincode.setText("")
        UpCompPhno.setText("")
        UpCompContactPerson.setText("")
        UpCompLicNo.setText("")
        UpCompGSTno.setText("")
        UpCompWebsite.setText("")
    }

    fun callService(id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("com_id",id)

                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/updatecompbtn.php")
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
                        var comp_name=js.getString("comp_name")
                        var comp_email=js.getString("comp_email")
                        var comp_city=js.getString("comp_city")
                        var comp_pincode=js.getString("comp_pincode")
                        var comp_mob1=js.getString("comp_mob1")
                        var comp_person=js.getString("comp_person")
                        var comp_licno=js.getString("comp_licno")
                        var comp_gstno=js.getString("comp_gstno")
                        var comp_website=js.getString("comp_website")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageCompany,"Data Found", Toast.LENGTH_LONG).show()

                                UpCompName.setText(comp_name).toString()
                                UpCompEmail.setText(comp_email).toString()
                                UpCompCity.setText(comp_city).toString()
                                UpCompPincode.setText(comp_pincode).toString()
                                UpCompPhno.setText(comp_mob1).toString()
                                UpCompContactPerson.setText(comp_person).toString()
                                UpCompLicNo.setText(comp_licno).toString()
                                UpCompGSTno.setText(comp_gstno).toString()
                                UpCompWebsite.setText(comp_website).toString()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageCompany,"No data found", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun callServiceUpdate(comp_id:String, comp_name:String, comp_email:String, comp_city:String, comp_pincode:String, comp_phno:String, comp_person:String, comp_licno:String, comp_gstno:String, comp_website:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("id",comp_id)
                .add("name",comp_name)
                .add("email",comp_email)
                .add("city",comp_city)
                .add("pincode",comp_pincode)
                .add("phno1",comp_phno)
                .add("contact_person",comp_person)
                .add("lic_no",comp_licno)
                .add("get_no",comp_gstno)
                .add("website",comp_website)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/updatecomp.php")
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
                                Toast.makeText(this@ManageCompany,"Updated Successfully!", Toast.LENGTH_LONG).show()
                                nulling()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@ManageCompany,"An Error Occurred!", Toast.LENGTH_LONG).show()
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
