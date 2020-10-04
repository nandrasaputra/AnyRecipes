package com.endiar.anyrecipes.ui.detail.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.IngredientAdapter
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.ui.detail.DetailSharedViewModel
import com.endiar.anyrecipes.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_overview.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class OverviewFragment : Fragment() {

    private val detailSharedViewModel: DetailSharedViewModel by sharedViewModel()
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupView()
    }

    private fun setupView() {
        ingredientAdapter = IngredientAdapter()
        fragment_overview_ingredient_recycle_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ingredientAdapter
            addItemDecoration(LinearItemDecoration(16, 4))
        }
    }

    private fun observeData() {
        detailSharedViewModel.detailRemoteDataMediatorLiveData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Success -> {
                        val ingredientList: List<String>? =
                            resource.data?.ingredients?.map { ingredient ->
                                ingredient.desc
                            }
                        ingredientAdapter.submitList(ingredientList)
                    }
                }
            }
        }
    }
}