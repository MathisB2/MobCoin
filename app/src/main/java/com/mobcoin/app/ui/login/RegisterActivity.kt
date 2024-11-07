package com.mobcoin.app.ui.login

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobcoin.app.CoinInfoViewModel
import com.mobcoin.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAppBar = binding.registerViewToolbar
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val registerViewModel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)


        val usernameInput = binding.loginViewNameInput
        val emailInput = binding.loginViewEmailInput
        val passwordInput = binding.registerViewPasswordInput
        val passwordConfirmInput = binding.registerViewPasswordConfirmInput

        val usernameInputLayout = binding.loginViewNameInputLayout
        val emailInputLayout = binding.loginViewEmailInputLayout
        val passwordInputLayout = binding.registerViewPasswordInputLayout
        val passwordConfirmInputLayout = binding.registerViewPasswordConfirmInputLayout


        val registerButton = binding.registerViewRegisterButton

        registerButton.setOnClickListener {
            usernameInputLayout.error = null
            emailInputLayout.error = null
            passwordInputLayout.error = null
            passwordConfirmInputLayout.error = null

            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()



            if(username.isEmpty()){
                usernameInputLayout.error = "Name is required"
                return@setOnClickListener
            }

            if(email.isEmpty()){
                emailInputLayout.error = "Email is required"
                return@setOnClickListener
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailInputLayout.error = "Please enter a valid email"
                return@setOnClickListener
            }


            if(password.isEmpty()){
                passwordInputLayout.error = "Password is required"
                return@setOnClickListener
            }
            else if (password.length < 4){
                passwordInputLayout.error = "Password must be at least 4 characters"
                return@setOnClickListener
            }

            if(passwordConfirmInput.text.toString() != password){
                passwordConfirmInputLayout.error = "Passwords doesn't match"
                return@setOnClickListener
            }

            registerViewModel.checkUserExists(email).observe(this) { exists ->
                if (exists) {
                    emailInputLayout.error = "This email is already used"
                }else{
                    registerViewModel.createUser(username, email, password)
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}