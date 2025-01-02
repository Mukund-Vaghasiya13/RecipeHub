package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolBarTB = findViewById<MaterialToolbar>(R.id.appBar)
        setSupportActionBar(toolBarTB)
        var signUpButton = findViewById<MaterialButton>(R.id.signup)
        signUpButton.setOnClickListener{
            val intent = Intent(applicationContext,SignupScreen::class.java)
            startActivity(intent)
        }

        var loginButton = findViewById<MaterialButton>(R.id.Login)
        loginButton.setOnClickListener{
            val intent = Intent(applicationContext,HomeScreen::class.java)
            startActivity(intent)
        }
    }

}