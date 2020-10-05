package com.endiar.anyrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endiar.anyrecipes.R
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var currentList: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient = currentList[position]
        holder.itemView.ingredient_item_text.text = currentIngredient
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(newList: List<String>?) {
        newList?.let {
            currentList = it
            notifyDataSetChanged()
        }
    }

    inner class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view)

}