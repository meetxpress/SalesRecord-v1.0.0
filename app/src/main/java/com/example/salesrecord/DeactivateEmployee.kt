package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_deactivate_employee.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DeactivateEmployee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deactivate_employee)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnSearchDeleteEmp.setOnClickListener {
            var id = etDelEmployee.text.toString();
            callService(id)
        }
    }

    fun callService(id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("emp_id",id)
                .build()

            var req= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/btndeactivateEmp.php")
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
                        var emp_name=js.getString("emp_name")
//                        var comp_id=js.getString("comp_id")
                        var emp_mob1=js.getString("emp_mob1")
                        var emp_address=js.getString("emp_address")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                //Toast.makeText(this@DeactivateEmployee,"Data Found", Toast.LENGTH_LONG).show()

                                val builder = AlertDialog.Builder(this@DeactivateEmployee)
                                builder.setTitle("Deactivate Employee")
                                builder.setMessage("Are you sure you want to deactivate this Employee?" +
                                        "\n\n" +
                                        "\nName: $emp_name " +
                                        "\nPhone No.: $emp_mob1" +
                                        "\nAddress: $emp_address")

                                builder.setPositiveButton(android.R.string.yes) { _, _ ->
                                    runOnUiThread {
                                        mainService(id)
                                    }
                                }

                                builder.setNegativeButton(android.R.string.no) {_, _ -> }
                                builder.show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@DeactivateEmployee,"No data found", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            })
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    //for updating the status of company in database
    fun mainService(id:String){
        try{
            var client2:OkHttpClient= OkHttpClient()

            var fBody= FormBody.Builder()
                .add("eid",id)
                .build()

            var req2= Request.Builder()
                .url("http://192.168.43.215/SalesRecord/deactivateEmp.php")
                .post(fBody)
                .build()

            client2.newCall(req2).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Exception", e.toString())
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
                            runOnUiThread {
                                Toast.makeText(this@DeactivateEmployee, message, Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread {
                                Toast.makeText(this@DeactivateEmployee, message, Toast.LENGTH_LONG).show()
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
