package com.endiar.anyrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.core.domain.model.RecipeByIngredients
import com.endiar.anyrecipes.ui.resultbyingredients.ResultByIngredientsFragmentDirections
import kotlinx.android.synthetic.main.search_result_by_ingredients_item.view.*

class ResultByIngredientRecipeAdapter : ListAdapter<RecipeByIngredients, ResultByIngredientRecipeAdapter.SearchResultByIngredientViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultByIngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_by_ingredients_item, parent, false)
        return SearchResultByIngredientViewHolder(view)
    }

    override fun onBindViewHolder(holderSearchResult: SearchResultByIngredientViewHolder, position: Int) {
        holderSearchResult.bind(getItem(position))
    }

    inner class SearchResultByIngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(recipe: RecipeByIngredients) {
            itemView.apply {
                search_result_by_ingredient_item_title.text = recipe.dishName
                if (recipe.dishImageUrl.isNotBlank()) {
                    Glide.with(context)
                        .load(recipe.dishImageUrl)
                        .placeholder(R.drawable.background_placeholder)
                        .into(search_result_by_ingredient_item_image)
                } else {
                    Glide.with(context)
                        .load(R.drawable.background_placeholder)
                        .into(search_result_by_ingredient_item_image)
                }
            }

            itemView.setOnClickListener {
                val action = ResultByIngredientsFragmentDirections.actionResultByIngredientsFragmentToDetailFragmentInFridge(recipe.dishId)
                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeByIngredients>() {
            override fun areItemsTheSame(oldItem: RecipeByIngredients, newItem: RecipeByIngredients): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: RecipeByIngredients, newItem: RecipeByIngredients): Boolean {
                return oldItem.dishId == newItem.dishId
            }
        }
    }

}