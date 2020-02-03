package com.example.salesrecord

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
/*
    private lateinit var loginActivity: LoginActivity

    data class LoginFormState(
        val usernameError: Int? = null,
        val passwordError: Int? = null,
        val isDataValid: Boolean = false
    )

    private val _loginForm = MutableLiveData<LoginFormState>()
    private val loginFormState: MutableLiveData<LoginFormState> = _loginForm
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

/*
        loginActivity= ViewModelProviders.of(this, loginFormState)
            .get(LoginActivity::class.java)

        loginActivity.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                LoginUsername.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                LoginPassword.error = getString(loginState.passwordError)
            }
        })

*/
        btnLogin.setOnClickListener {
            if((LoginUsername.text.toString().trim() == "") and (LoginPassword.text.toString().trim() == "")){
                Toast.makeText(this,"Please Enter Username and Password to Login!",Toast.LENGTH_LONG).show()
            }
            else if((LoginUsername.text.toString().equals("admin")) and (LoginPassword.text.toString().equals("aadmin"))) {
                Toast.makeText(this, "Welcome " + LoginUsername.text, Toast.LENGTH_LONG).show()
                var i = Intent(this@LoginActivity, SuperAdminHome::class.java)
                startActivity(i)
                finish()
            }
            else if((LoginUsername.text.toString().equals("comp")) and (LoginPassword.text.toString().equals("ccomp"))) {
                Toast.makeText(this, "Welcome " + LoginUsername.text, Toast.LENGTH_LONG).show()
                var i = Intent(this@LoginActivity, DistributerHome::class.java)
                startActivity(i)
                finish()
            }
            else {
                Toast.makeText(this,"Invalid Username and Password",Toast.LENGTH_LONG).show()
                LoginPassword.text==null
            }
        }
    }
}
