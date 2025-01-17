package com.example.recipehub.Adpter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipehub.R
import com.example.recipehub.modle.Recipe
import com.squareup.picasso.Picasso

class ListRecipeAdapter(val Conent:Activity,val recipes: List<Recipe>) : RecyclerView.Adapter<ListRecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.RecipeImage)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layout = LayoutInflater.from(Conent).inflate(R.layout.eachrecipe, parent, false)
        return RecipeViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.description.text = recipe.description ?: "No description available"

        val imageUrl = recipe.image
        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(holder.imageView)
        } else {
           // Fallback image
            Picasso.get().load("https://miro.medium.com/v2/resize:fit:1400/1*MXyMqcEJ6Se0SCWcYCKZTQ.jpeg").into(holder.imageView)
        }
    }
}
