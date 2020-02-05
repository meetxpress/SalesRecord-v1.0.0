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
            if((CompName.toString().length <0) and
                (CompEmail.toString().length <0) and
                (CompCity.toString().length <0) and
                (CompPincode.toString().length <0) and
                (CompPhno.toString().length <0) and
                (CompContactPerson.toString().length <0) and
                (CompLicNo.toString().length <0) and
                (CompGSTno.toString().length <0) and
                (CompWebsite.toString().length <0))
            {
                Toast.makeText(this,CompName.toString().length,Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this,CompName.text.toString(),Toast.LENGTH_LONG).show()
                CompName.setText("")
                CompEmail.setText("")  
                CompCity.setText("")
                CompPincode.setText("")
                CompPhno.setText("")
                CompContactPerson.setText("")
                CompLicNo.setText("")
                CompGSTno.setText("")
                CompWebsite.setText("")
            }
        }
    }
}
