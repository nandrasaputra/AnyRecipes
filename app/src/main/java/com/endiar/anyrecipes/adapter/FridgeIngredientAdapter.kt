package com.endiar.anyrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.model.FridgeIngredientItem
import com.endiar.anyrecipes.model.FridgeIngredientItemState
import kotlinx.android.synthetic.main.fridge_ingredient_item.view.*

class FridgeIngredientAdapter(
    private val fridgeMenuStateMap: MutableMap<Int, FridgeIngredientItemState>,
    private val onCheckStateChangeCallback: (MutableMap<Int, FridgeIngredientItemState>) -> Unit
): ListAdapter<FridgeIngredientItem, FridgeIngredientAdapter.FridgeIngredientViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeIngredientViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fridge_ingredient_item_header, parent, false)
            FridgeIngredientViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fridge_ingredient_item, parent, false)
            FridgeIngredientViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onBindViewHolder(holder: FridgeIngredientViewHolder, position: Int) {
        if (position != 0) {
            holder.bind(getItem(position-1))
        }
    }

    inner class FridgeIngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(ingredient: FridgeIngredientItem) {

            val checkedStateMap = fridgeMenuStateMap[ingredient.ingredientId]?.isChecked

            itemView.apply {

                fridge_ingredient_item_checkbox.isChecked = checkedStateMap ?: false
                fridge_ingredient_item_title.text = ingredient.ingredientName

                Glide.with(context)
                    .load(ingredient.resourceImageId)
                    .into(fridge_ingredient_item_image)
            }

            itemView.setOnClickListener {
                itemView.fridge_ingredient_item_checkbox.toggle()
                fridgeMenuStateMap[ingredient.ingredientId] = FridgeIngredientItemState(ingredient.ingredientId, ingredient.ingredientValue, itemView.fridge_ingredient_item_checkbox.isChecked)
                onCheckStateChangeCallback(fridgeMenuStateMap)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_INGREDIENTS
        }
    }

    companion object {

        private const val TYPE_HEADER = 1
        private const val TYPE_INGREDIENTS = 2

        private val diffUtil = object : DiffUtil.ItemCallback<FridgeIngredientItem>() {
            override fun areItemsTheSame(oldItem: FridgeIngredientItem, newItem: FridgeIngredientItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FridgeIngredientItem, newItem: FridgeIngredientItem): Boolean {
                return oldItem.ingredientId == newItem.ingredientId
            }
        }
    }
}