package com.endiar.anyrecipes.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.endiar.anyrecipes.ui.detail.nutrition.NutritionFragment
import com.endiar.anyrecipes.ui.detail.overview.OverviewFragment
import com.endiar.anyrecipes.ui.detail.step.StepFragment

class DetailViewPagerAdapter(
    fragment: Fragment,
    private val numberOfTab: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return numberOfTab
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { OverviewFragment() }
            1 -> { StepFragment() }
            2 -> { NutritionFragment() }
            else -> throw Exception("Position: $position is not available!")
        }
    }
}