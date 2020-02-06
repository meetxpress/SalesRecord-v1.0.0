package com.example.salesrecord

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.CalendarContract
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_employee.*
import java.util.*

class RegisterEmployee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employee)
    }

    fun addDoB(view:View) {
        var dpd:DatePickerDialog
        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)
        val date=findViewById<EditText>(R.id.EmpDoB)
        var d:Date
        dpd=DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener {
                view,year,month,day->date.setText(" $day / ${month+1} / $year")
        },year,month,day)
        //if(dpd > c.get(Calendar.DATE)) {
            dpd.show()
        //}
    }

    fun onRadioButtonClicked(view: View) {
        var checked = view as RadioButton
        if (rb_male == checked) {
            Toast.makeText(this, (rb_male.text.toString() + if (rb_male.isChecked) " Checked " else " UnChecked "), Toast.LENGTH_LONG).show()
        }
        if (rb_female == checked) {
            Toast.makeText(this, (rb_female.text.toString() + if (rb_female.isChecked) " Checked " else " UnChecked "), Toast.LENGTH_LONG).show()
        }
    }
}
