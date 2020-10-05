package com.endiar.anyrecipes.ui.detail

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.DetailViewPagerAdapter
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.core.domain.model.RecipeFull
import com.endiar.anyrecipes.utils.dpToPx
import com.endiar.anyrecipes.utils.tabTitleProvider
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private val detailSharedViewModel: DetailSharedViewModel by sharedViewModel()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var detailViewPagerAdapter: DetailViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
                    is Resource.Loading -> {
                        fragment_detail_shimmer_layout.visibility = View.VISIBLE
                        fragment_detail_scroll_view.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        bindRemoteData(resource.data)
                        fragment_detail_shimmer_layout.visibility = View.GONE
                        fragment_detail_scroll_view.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        fragment_detail_shimmer_layout.visibility = View.GONE
                        fragment_detail_scroll_view.visibility = View.GONE
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        detailSharedViewModel.detailLocalDataMediatorLiveData.observe(viewLifecycleOwner) { favoriteStatus ->
            handleLocalData(favoriteStatus)
        }
    }

    private fun setupView() {
        val id = args.recipeId
        detailSharedViewModel.getDetailData(id)

        fragment_detail_favorite_image.setOnClickListener {
            detailSharedViewModel.toggleFavorite()
        }

        fragment_detail_toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_toolbar)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }

        fragment_detail_view_pager.offscreenPageLimit = 1

        // Disable overScrollMode On ViewPager2
        (fragment_detail_view_pager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        detailViewPagerAdapter = DetailViewPagerAdapter(this, fragment_detail_tab_layout.tabCount)
        fragment_detail_view_pager.adapter = detailViewPagerAdapter

        TabLayoutMediator(fragment_detail_tab_layout, fragment_detail_view_pager) { tab, position ->
            tab.text = tabTitleProvider(position)
        }.attach()

        fragment_detail_view_pager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val view = childFragmentManager.findFragmentByTag("f$position")?.view
                adjustViewPagerFragmentHeight(view)
            }
        })
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

            if (it.dishImageUrl.isNotBlank()) {
                Glide.with(requireContext())
                    .load(it.dishImageUrl)
                    .into(fragment_detail_dish_image)
            } else {
                Glide.with(requireContext())
                    .load(R.drawable.background_placeholder)
                    .into(fragment_detail_dish_image)
            }

            handleDietAppearance(it.isGlutenFree, it.isDairyFree, it.isVegetarian)
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

    private fun handleDietAppearance(
        isGlutenFree: Boolean,
        isDairyFree: Boolean,
        isVegetarian: Boolean
    ) {
        if (isGlutenFree) {
            fragment_detail_gluten_text.text = "Gluten\nFree"
        } else {
            fragment_detail_gluten_text.text = "Contain\nGluten"
        }

        if (isDairyFree) {
            fragment_detail_dairy_text.text = "Dairy\nFree"
        } else {
            fragment_detail_dairy_text.text = "Contain\nDairy"
        }

        if (isVegetarian) {
            fragment_detail_vegan_text.text = "Safe For\nVegan"
        } else {
            fragment_detail_vegan_text.text = "Not Safe\nFor Vegan"
        }
    }

    private fun adjustViewPagerFragmentHeight(view: View?) {
        view?.let {
            it.post {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                val desiredMinHeight = displayMetrics.heightPixels - dpToPx(requireContext(), 256)

                val wMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(it.width, View.MeasureSpec.EXACTLY)
                val hMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                it.measure(wMeasureSpec, hMeasureSpec)

                val desiredHeight = if (desiredMinHeight < it.measuredHeight) {
                    it.measuredHeight
                } else {
                    desiredMinHeight
                }

                fragment_detail_view_pager.layoutParams =
                    (fragment_detail_view_pager.layoutParams as ConstraintLayout.LayoutParams)
                        .also { layoutParams -> layoutParams.height = desiredHeight }

                it.layoutParams = (it.layoutParams as FrameLayout.LayoutParams)
                    .also { layoutParams -> layoutParams.height = desiredHeight}
            }
        }
    }
}