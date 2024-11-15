package com.mobcoin.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.mobcoin.app.R

class LoginActivity : AppCompatActivity() {

    lateinit var vm: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        vm = ViewModelProvider(this).get(LoginViewModel::class.java)

        val topAppBar = findViewById<MaterialToolbar>(R.id.loginView_toolbar)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val emailInput = findViewById<EditText>(R.id.loginView_email_input)
        val passwordInput = findViewById<EditText>(R.id.loginView_password_input)


        findViewById<MaterialButton>(R.id.loginView_login_button).setOnClickListener {


            vm.login(
                email = emailInput.text.toString(),
                password = passwordInput.text.toString(),
                context = this,
                onSuccess = {
                    Toast.makeText(this, "login successfull", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onFailure = {
                    Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                }
            )
        }





        findViewById<MaterialButton>(R.id.loginView_register_button).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}