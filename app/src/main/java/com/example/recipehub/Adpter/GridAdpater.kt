package com.example.recipehub.Adpter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipehub.Adpter.ListRecipeAdapter.RecipeViewHolder
import com.example.recipehub.R
import com.example.recipehub.modle.Recipe
import com.squareup.picasso.Picasso

class GridAdpater(val Conent: Activity, val recipes: List<Recipe>,) : RecyclerView.Adapter<GridAdpater.RecipeGridViewHolder>()  {

    class RecipeGridViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.RecipeImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeGridViewHolder {
        val layout = LayoutInflater.from(Conent).inflate(R.layout.each_grid_item, parent, false)
        return RecipeGridViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RecipeGridViewHolder, position: Int) {
        val recipe = recipes[position]
        Picasso.get().load(recipe.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return  recipes.size
    }

}