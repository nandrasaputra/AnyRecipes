package com.endiar.anyrecipes.favoritefeature.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.endiar.anyrecipes.favoritefeature.R
import com.endiar.anyrecipes.favoritefeature.adapter.FavoriteRecipeAdapter
import com.endiar.anyrecipes.favoritefeature.di.favoriteModule
import com.endiar.anyrecipes.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
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

                if (it.isNotEmpty()) {
                    fragment_favorite_lottie_layout.visibility = View.GONE
                } else {
                    fragment_favorite_lottie_layout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupView() {
        favoriteRecipeAdapter = FavoriteRecipeAdapter {
            favoriteViewModel.removeRecipeFromFavorite(it)
        }

        fragment_favorite_recycler_view.apply {
            adapter = favoriteRecipeAdapter
            addItemDecoration(LinearItemDecoration())
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}