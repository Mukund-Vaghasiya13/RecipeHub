package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlin.system.exitProcess

class ProfileScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_screen)

        val sharedPF = getSharedPreferences("RecipeHubSh",Context.MODE_PRIVATE)
        val email = sharedPF.getString("email","Error")
        val profilePicture = sharedPF.getString("profilePicture","https://miro.medium.com/v2/resize:fit:1400/1*MXyMqcEJ6Se0SCWcYCKZTQ.jpeg")
        val username = sharedPF.getString("username","OOPS Server Error!")

        val profileBar = findViewById<MaterialToolbar>(R.id.profilebar)
        setSupportActionBar(profileBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        profileBar.title = username.toString()

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

        val imageView = findViewById<ImageView>(R.id.imageView3)
        Picasso.get().load(profilePicture).into(imageView)


        val emailHolder = findViewById<TextView>(R.id.textView2)
        emailHolder.text = email
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profilemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.logout ->{
                val shared = getSharedPreferences("RecipeHubSh", Context.MODE_PRIVATE)
                val edit = shared.edit()
                edit.remove("LoginToken")
                edit.apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish();
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}