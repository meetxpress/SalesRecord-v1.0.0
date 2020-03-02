package com.example.salesrecord

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.util.*

class ManageEmployee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_employee)
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
}
