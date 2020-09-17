package com.endiar.anyrecipes.ui.fridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.FridgeIngredientAdapter
import com.endiar.anyrecipes.model.FridgeIngredientItemState
import com.endiar.anyrecipes.utils.Data.fridgeData
import com.endiar.anyrecipes.utils.FridgeIngredientDecoration
import com.endiar.anyrecipes.utils.convertMapToIngredients
import com.endiar.anyrecipes.utils.dpToPx
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.fridge_ingredient_chosen_float_view.*
import org.koin.android.viewmodel.ext.android.viewModel


class FridgeFragment : Fragment() {

    private val fridgeViewModel: FridgeViewModel by viewModel()
    private lateinit var fridgeIngredientAdapter: FridgeIngredientAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {

        fragment_fridge_float_view.setOnClickListener {
            val ingredients = convertMapToIngredients(fridgeViewModel.fridgeIngredientItemStateMap.value ?: mutableMapOf())
            if (ingredients.isNotBlank()) {
                val action = FridgeFragmentDirections.actionFridgeFragmentToSearchResultFragment(ingredients)
                findNavController().navigate(action)
            }
        }

        fridgeIngredientAdapter = FridgeIngredientAdapter(
            fridgeViewModel.fridgeIngredientItemStateMap.value ?: mutableMapOf()
        ) { updateViewModelMap(it) }

        val gridLayoutManager = GridLayoutManager(requireContext(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) 3 else 1
                }
            }
        }
        fragment_fridge_recycler_view.apply {
            layoutManager = gridLayoutManager
            adapter = fridgeIngredientAdapter
            addItemDecoration(FridgeIngredientDecoration(16, 16, 1))
        }

        fridgeIngredientAdapter.submitList(fridgeData)
    }

    private fun observeData() {
        fridgeViewModel.fridgeIngredientItemStateMap.observe(viewLifecycleOwner) { mutableMap ->
            handleFridgeIngredientItemStateMapChange(mutableMap)
        }
    }

    private fun handleFridgeIngredientItemStateMapChange(map: MutableMap<Int, FridgeIngredientItemState>) {
        if (map.isNotEmpty()) {
            var ingredientsCount = 0
            for ((_, itemState) in map) {
                if (itemState.isChecked) {
                    ingredientsCount++
                }
            }
            toggleFridgeChosenFloatView(ingredientsCount != 0, ingredientsCount)
        }
    }

    private fun updateViewModelMap(newMap: MutableMap<Int, FridgeIngredientItemState>) {
        fridgeViewModel.updateFridgeIngredientItemStateMap(newMap)
    }

    private fun toggleFridgeChosenFloatView(isShow: Boolean, ingredientCount: Int) {
        if (isShow) {
            if (fragment_fridge_float_view.visibility != View.VISIBLE) {
                fragment_fridge_float_view.visibility = View.VISIBLE
                fragment_fridge_recycler_view.setPadding(0,0,0, dpToPx(requireContext(), 64))
            }
            val ingredientCountTextPlaceholder = "$ingredientCount Ingredients Selected"
            fridge_ingredient_chosen_float_view_count.text = ingredientCountTextPlaceholder
        } else {
            if (fragment_fridge_float_view.visibility == View.VISIBLE) {
                fragment_fridge_float_view.visibility = View.GONE
                fragment_fridge_recycler_view.setPadding(0,0,0, 0)
            }
            fragment_fridge_float_view.visibility = View.INVISIBLE
        }
    }
}