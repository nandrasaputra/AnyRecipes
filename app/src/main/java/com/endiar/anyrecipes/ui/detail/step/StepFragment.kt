package com.endiar.anyrecipes.ui.detail.step

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
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_step.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class StepFragment : Fragment() {

    private val detailSharedViewModel: DetailSharedViewModel by sharedViewModel()
    private lateinit var stepAdapter: IngredientAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
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
                        val stepList: List<String>? =
                            resource.data?.steps?.map { step ->
                                "${step.number}. ${step.stepInstruction}"
                            }
                        stepAdapter.submitList(stepList)
                    }
                }
            }
        }
    }

    private fun setupView() {
        stepAdapter = IngredientAdapter()
        fragment_step_step_recycle_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stepAdapter
            addItemDecoration(LinearItemDecoration(16, 4))
        }
    }
}