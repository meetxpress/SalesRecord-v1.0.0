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

        fun nulling(){
            UpCompName.text=null
            UpCompPhno.text=null
            UpCompEmail.text=null
            UpCompAddress.text=null
            UpCompLicNo.text=null
            UpOwnerName.text=null
            UpOwnerPhno.text=null
            UpOwnerEmail.text=null
            UpOwnerAddress.text=null
            UpOwnerAadharCard.text=null
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
