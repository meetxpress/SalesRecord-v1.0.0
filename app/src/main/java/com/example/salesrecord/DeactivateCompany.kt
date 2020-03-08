package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_deactivate_company.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DeactivateCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deactivate_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

        btnSearchDeleteComp.setOnClickListener {
            var id = etDelCompany.text.toString();
            callService(id)
        }
    }

    fun callService(id:String){
        try{
            var client= OkHttpClient()

            var formBody= FormBody.Builder()
                .add("com_id",id)
                .build()

            var req= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/btndeactivateComp.php")
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
                        var comp_city=js.getString("comp_city")
                        var comp_mob1=js.getString("comp_mob1")
                        var comp_person=js.getString("comp_person")
                        var comp_licno=js.getString("comp_licno")

                        Log.v("res",str)

                        if(flag == 1){
                            Log.v("fs", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@DeactivateCompany,"Data Found", Toast.LENGTH_LONG).show()

                                val builder = AlertDialog.Builder(this@DeactivateCompany)
                                builder.setTitle("Deactivate Company")
                                builder.setMessage("Are you sure you want to deactivate this company?" +
                                        "\n\n" +
                                        "\nName: ${comp_name} " +
                                        "\nCity: ${comp_city}" +
                                        "\nPhone No.: ${comp_mob1}" +
                                        "\nContact Person: ${comp_person}" +
                                        "\nLicense No: ${comp_licno}\n")

                                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                    runOnUiThread {
                                       // Toast.makeText(this@DeactivateCompany,android.R.string.yes, Toast.LENGTH_SHORT).show()
                                        mainService(id)
                                    }
                                }

                                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                                    /*runOnUiThread {
                                        Toast.makeText(this@DeactivateCompany,android.R.string.no, Toast.LENGTH_SHORT).show()
                                    }*/
                                }
                                builder.show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread{
                                Toast.makeText(this@DeactivateCompany,"No data found", Toast.LENGTH_LONG).show()
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
            var client2= OkHttpClient()

            var fBody= FormBody.Builder()
                .add("cid",id)
                .build()

            var req2= Request.Builder()
                .url("http://10.0.2.2:80/SalesRecord/deactivateComp.php")
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
                                Toast.makeText(this@DeactivateCompany,"Deactivated Successfully!", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Log.v("ff", flag.toString())
                            runOnUiThread {
                                Toast.makeText(this@DeactivateCompany,"An Error Occurred!", Toast.LENGTH_LONG).show()
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
