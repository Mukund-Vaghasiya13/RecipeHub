package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.recipehub.AppInterface.ApiInterface
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val url = "http://localhost:3000/api/v1/User/login"

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
        val eoufield = findViewById<TextInputEditText>(R.id.eouname)
        val passwordfield = findViewById<TextInputEditText>(R.id.password)

        var loginButton = findViewById<MaterialButton>(R.id.Login)
        loginButton.setOnClickListener{

            if (eoufield.text!!.isNotEmpty() && passwordfield.text!!.isNotEmpty()) {
                val loginParam = mapOf(
                    "usernameOrEmail" to eoufield.text.toString(),
                    "password" to passwordfield.text.toString()
                )
                LoginCall(loginParam)
            } else {
                // Handle the case when either field is empty
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }


//            val intent = Intent(applicationContext,HomeScreen::class.java)
//            startActivity(intent)
        }
    }

    private fun LoginCall(param:Map<String,String>){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val  retrodata = retrofitBuilder.login(param)
        retrodata.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val statusCode = response.code()
                if(statusCode == 201){
                   Toast.makeText(applicationContext,response.body().toString(),Toast.LENGTH_LONG).show()
                    println(response.body().toString())
                }else{
                    Toast.makeText(applicationContext,statusCode.toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}