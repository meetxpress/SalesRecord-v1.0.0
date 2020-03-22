package com.example.salesrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_emp_leave_report.*
import java.text.SimpleDateFormat
import java.util.*

class EmpLeaveReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_leave_report)

        leaveDate.setText(SimpleDateFormatateFormat("MM-YYYY").format(Calendar.getInstance().time)).toString()
    }
}
