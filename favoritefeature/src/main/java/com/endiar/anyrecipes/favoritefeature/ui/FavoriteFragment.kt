package com.endiar.anyrecipes.favoritefeature.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.endiar.anyrecipes.favoritefeature.R
import com.endiar.anyrecipes.favoritefeature.adapter.FavoriteRecipeAdapter
import com.endiar.anyrecipes.favoritefeature.di.favoriteModule
import com.endiar.anyrecipes.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setupView()
    }

    private fun observeData() {
        favoriteViewModel.favoriteMediatorLiveData.observe(viewLifecycleOwner) { favoriteList ->
            favoriteList?.let {
                favoriteRecipeAdapter.submitList(it)

                fragment_favorite_lottie_layout.visibility = if (it.isNotEmpty()) GONE else VISIBLE
            }
        }
    }

    private fun setupView() {
        favoriteRecipeAdapter = FavoriteRecipeAdapter {
            favoriteViewModel.removeRecipeFromFavorite(it)
        }

        fragment_favorite_recycler_view.apply {
            adapter = favoriteRecipeAdapter
            addItemDecoration(LinearItemDecoration(16, 16))
        }
    }
}