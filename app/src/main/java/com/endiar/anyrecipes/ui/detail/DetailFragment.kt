package com.endiar.anyrecipes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupView()
    }

    private fun observeData() {
        detailViewModel.detailRemoteDataMediatorLiveData.observe( viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_detail_progress_bar.visibility = View.VISIBLE
                        fragment_detail_scroll_view.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bindRemoteData(resource.data)
                        fragment_detail_progress_bar.visibility = View.GONE
                        fragment_detail_scroll_view.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        fragment_detail_progress_bar.visibility = View.GONE
                        fragment_detail_scroll_view.visibility = View.GONE
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        detailViewModel.detailLocalDataMediatorLiveData.observe(viewLifecycleOwner) { favoriteStatus ->
                handleLocalData(favoriteStatus)
            }
    }

    private fun setupView() {
        val id = args.recipeId
        detailViewModel.getDetailData(id)

        fragment_detail_favorite_image.setOnClickListener {
            detailViewModel.toggleFavorite()
        }

        fragment_detail_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }

    private fun bindRemoteData(recipe: RecipeFull?) {
        recipe?.let {
            val creditText = "by " + it.creditText
            val likeCount = it.likesCount.toString() + " Likes"
            val cookingTime = it.cookingTime.toString() + " Minutes"

            fragment_detail_dish_name.text = it.dishName
            fragment_detail_credit_owner.text = creditText
            fragment_detail_like_count_text.text = likeCount
            fragment_detail_time_count_text.text = cookingTime
            fragment_detail_instruction_desc.text = it.shortInstruction

            if (it.dishImageUrl.isNotBlank()) {
                Glide.with(requireContext())
                    .load(it.dishImageUrl)
                    .into(fragment_detail_dish_image)
            } else {
                Glide.with(requireContext())
                    .load(R.drawable.background_placeholder)
                    .into(fragment_detail_dish_image)
            }
        }
    }

    private fun handleLocalData(favoriteStatus: Boolean?) {
        favoriteStatus?.let { isFavorite ->
            if (isFavorite) {
                Glide.with(requireContext())
                    .load(R.drawable.ic_heart_filled)
                    .into(fragment_detail_favorite_image)
            } else {
                Glide.with(requireContext())
                    .load(R.drawable.ic_heart_lineal)
                    .into(fragment_detail_favorite_image)
            }
        }
    }
}