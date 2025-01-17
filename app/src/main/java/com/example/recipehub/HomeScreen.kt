package com.example.recipehub

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipehub.Adpter.ListRecipeAdapter
import com.example.recipehub.AppInterface.ApiInterface
import com.example.recipehub.modle.MYError
import com.example.recipehub.modle.Recipe
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class HomeScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        val homeAppbar = findViewById<MaterialToolbar>(R.id.homebar)
        setSupportActionBar(homeAppbar)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val errorMessageView = findViewById<TextView>(R.id.error_message)
        NetworkCallListRecipe(1) { list, message ->
            if (message != null) {
                recycler.visibility = View.GONE
                errorMessageView.visibility = View.VISIBLE
                errorMessageView.text = "Error: $message"
            } else if (list != null) {
                recycler.visibility = View.VISIBLE
                errorMessageView.visibility = View.GONE
                Log.d("HomeScreen", "Fetched ${list.size} recipes.")

                val myAdapter = ListRecipeAdapter(this, list)
                recycler.adapter = myAdapter
                recycler.layoutManager = LinearLayoutManager(this)
            } else {
                Log.e("HomeScreen", "Failed to fetch recipes.")
                recycler.visibility = View.GONE
                errorMessageView.visibility = View.VISIBLE
                errorMessageView.text = "Failed to fetch recipes."
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                val intent = Intent(applicationContext, ProfileScreen::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun NetworkCallListRecipe(page: Int, callback: (List<Recipe>?, String?) -> Unit) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://sharerecipy-backend.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val sharedPF = getSharedPreferences("RecipeHubSh", MODE_PRIVATE)
        val token = sharedPF.getString("LoginToken", null)

        if (token != null) {
            val retroData = retrofitBuilder.ListRecipe(page, token)
            retroData.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val statusCode = response.code()
                    if (statusCode == 200) {
                        val recipeList = Gson().fromJson(response.body()?.string(), Array<Recipe>::class.java).toList()
                        callback(recipeList, null)
                    } else {
                        callback(null, parseErrorResponse(response.body()))
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    if (t is IOException) {
                        callback(null, "Network error, please check your connection.")
                    } else {
                        callback(null, "Unexpected error: ${t.message}")
                    }
                }
            })
        } else {
            callback(null, "Token is missing")
        }
    }

    private fun parseErrorResponse(responseBody: ResponseBody?): String {
        return try {
            val jsonString = responseBody?.string()
            val myError = Gson().fromJson(jsonString, MYError::class.java)
            myError.technicalDetails
        } catch (e: Exception) {
            "Unknown Error"
        }
    }
}
