package com.endiar.anyrecipes.favoritefeature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.core.domain.model.FavoriteRecipe
import com.endiar.anyrecipes.favoritefeature.R
import com.endiar.anyrecipes.favoritefeature.ui.FavoriteFragmentDirections
import kotlinx.android.synthetic.main.favorite_recipe.view.*

class FavoriteRecipeAdapter(
    private val onRemoveButtonClickCallback: (Int) -> Unit
) : ListAdapter<FavoriteRecipe, FavoriteRecipeAdapter.FavoriteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_recipe, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(favoriteRecipe: FavoriteRecipe) {
            itemView.apply {
                favorite_recipe_remove_button.setOnClickListener {
                    onRemoveButtonClickCallback(favoriteRecipe.dishId)
                }

                favorite_recipe_title.text = favoriteRecipe.dishName
                favorite_recipe_credit.text = favoriteRecipe.creditText

                Glide.with(context)
                    .load(favoriteRecipe.dishImageUrl)
                    .placeholder(com.endiar.anyrecipes.R.drawable.background_placeholder)
                    .into(favorite_recipe_image)
            }

            itemView.setOnClickListener {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragmentInFavorite(favoriteRecipe.dishId)
                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {

        private val diffUtil = object : DiffUtil.ItemCallback<FavoriteRecipe>() {
            override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
                return oldItem.dishId == newItem.dishId
            }
        }
    }
}