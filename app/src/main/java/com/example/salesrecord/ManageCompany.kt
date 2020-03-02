package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_manage_company.*
import kotlinx.android.synthetic.main.activity_register_company.*

class ManageCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_company)

        //back button on actionbar
        supportActionBar?.setDisplayShowCustomEnabled(true)

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

        btnUpdateComp.setOnClickListener {
            Toast.makeText(this,"Company details updated Successfully",Toast.LENGTH_LONG).show()
            nulling()
        }

        btnDeleteComp.setOnClickListener {
            Toast.makeText(this,"Company Deactivated Successfully.",Toast.LENGTH_LONG).show()
            nulling()
        }
    }
}
