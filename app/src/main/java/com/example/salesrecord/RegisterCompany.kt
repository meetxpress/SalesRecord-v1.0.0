package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_company.*

class RegisterCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_company)

        btnRegisterCompany.setOnClickListener {
            if((CompName.toString().length <0) and (CompPhno.toString().length <0) and (CompEmail.toString().length <0) and (CompLicNo.toString().length <0) and (OwnerName.toString().length <0) and(OwnerPhno.toString().length <0) and(OwnerEmail.toString().length <0) and(OwnerAddress.toString().length <0) and (OwnerAadharCard.toString().length <0)){
                Toast.makeText(this,CompName.toString().length,Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this,CompName.text.toString(),Toast.LENGTH_LONG).show()
                CompName.text=null
                CompPhno.text=null
                CompEmail.text=null
                CompAddress.text=null
                CompLicNo.text=null
                OwnerName.text=null
                OwnerPhno.text=null
                OwnerEmail.text=null
                OwnerAddress.text=null
                OwnerAadharCard.text=null
            }
        }
    }
}
