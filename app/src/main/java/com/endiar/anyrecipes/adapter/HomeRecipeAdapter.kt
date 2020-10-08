package com.endiar.anyrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.core.domain.model.RecipeGist
import com.endiar.anyrecipes.ui.home.HomeFragmentDirections
import com.endiar.anyrecipes.utils.loadImage
import kotlinx.android.synthetic.main.recipes_item.view.*

class HomeRecipeAdapter : ListAdapter<RecipeGist, HomeRecipeAdapter.HomeRecipeViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_item, parent, false)
        return HomeRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holderHome: HomeRecipeViewHolder, position: Int) {
        holderHome.bind(getItem(position))
    }

    inner class HomeRecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(recipeGist: RecipeGist) {
            itemView.apply {
                val minuteTextPlaceHolder = recipeGist.cookingTime.toString() + " Minutes"
                val likeCountTextPlaceHolder = recipeGist.likesCount.toString() + " Likes"
                recipes_item_dish_name.text = recipeGist.dishName
                recipes_item_time_count_text.text = minuteTextPlaceHolder
                recipes_item_like_count_text.text = likeCountTextPlaceHolder
                recipes_item_author_text.text = recipeGist.creditText

                context.loadImage(recipeGist.dishImageUrl, recipes_item_dish_image)
            }
            itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(recipeGist.dishId)
                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeGist>() {
            override fun areItemsTheSame(oldItem: RecipeGist, newItem: RecipeGist): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: RecipeGist, newItem: RecipeGist): Boolean {
                return oldItem.dishId == newItem.dishId
            }
        }
    }

}