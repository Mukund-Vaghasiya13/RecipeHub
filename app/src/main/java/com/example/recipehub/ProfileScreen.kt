package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class ProfileScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_screen)
        val profileBar = findViewById<MaterialToolbar>(R.id.profilebar)
        setSupportActionBar(profileBar)

        val editButton = findViewById<MaterialButton>(R.id.editProfile)
        editButton.setOnClickListener{
            val intent = Intent(applicationContext,EditProfileScreen::class.java)
            startActivity(intent)
        }

        val addRecipy = findViewById<MaterialButton>(R.id.addRecipe)
        addRecipy.setOnClickListener{
            val intent = Intent(applicationContext,AddRecipeScreen::class.java)
            startActivity(intent)
        }
    }
}