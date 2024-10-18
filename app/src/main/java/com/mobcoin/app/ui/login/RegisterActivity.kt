package com.mobcoin.app.ui.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.mobcoin.app.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val topAppBar = findViewById<MaterialToolbar>(R.id.registerView_toolbar)
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}