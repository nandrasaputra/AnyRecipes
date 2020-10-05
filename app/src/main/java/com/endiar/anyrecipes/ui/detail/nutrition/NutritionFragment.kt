package com.endiar.anyrecipes.ui.detail.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.IngredientAdapter
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.ui.detail.DetailSharedViewModel
import com.endiar.anyrecipes.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_nutrition.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NutritionFragment : Fragment() {

    private val detailSharedViewModel: DetailSharedViewModel by sharedViewModel()
    private lateinit var nutritionAdapter: IngredientAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupView()
    }

    private fun observeData() {
        detailSharedViewModel.detailRemoteDataMediatorLiveData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Success -> {
                        val ingredientList: List<String>? =
                            resource.data?.nutrients?.map { nutrient ->
                                "${nutrient.title}: ${nutrient.amount} ${nutrient.unit}"
                            }
                        nutritionAdapter.submitList(ingredientList)
                    }
                }
            }
        }
    }

    private fun setupView() {
        nutritionAdapter = IngredientAdapter()
        fragment_nutrition_nutrition_recycle_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nutritionAdapter
            addItemDecoration(LinearItemDecoration(16, 4))
        }
    }
}