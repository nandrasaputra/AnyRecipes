package com.endiar.anyrecipes.ui.resultbyingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.ResultByIngredientRecipeAdapter
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.utils.ResultByIngredientsItemDecoration
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.android.viewmodel.ext.android.viewModel

class ResultByIngredientsFragment: Fragment() {

    private lateinit var resultByIngredientRecipeAdapter: ResultByIngredientRecipeAdapter
    private val resultByIngredientsViewModel: ResultByIngredientsViewModel by viewModel()
    private val args: ResultByIngredientsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun observeData() {
        resultByIngredientsViewModel.resultMediatorLiveData.observe( viewLifecycleOwner, Observer { resource ->
                if (resource != null) {
                    when (resource) {
                        is Resource.Loading -> {
                            fragment_search_progress_bar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            fragment_search_progress_bar.visibility = View.GONE
                            resultByIngredientRecipeAdapter.submitList(resource.data)
                        }
                        is Resource.Error -> {
                            fragment_search_progress_bar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "${resource.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    private fun setupView() {
        val ingredients = args.ingredients
        resultByIngredientsViewModel.getRecipeByIngredients(ingredients)

        resultByIngredientRecipeAdapter = ResultByIngredientRecipeAdapter()

        fragment_search_recycler_view.apply {
            adapter = resultByIngredientRecipeAdapter
            addItemDecoration(ResultByIngredientsItemDecoration(16, 16))
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        fragment_search_result_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }
}