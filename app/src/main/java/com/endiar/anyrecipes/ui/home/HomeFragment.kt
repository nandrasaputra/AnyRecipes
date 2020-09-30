package com.endiar.anyrecipes.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.endiar.anyrecipes.R
import com.endiar.anyrecipes.adapter.HomeRecipeAdapter
import com.endiar.anyrecipes.core.data.Resource
import com.endiar.anyrecipes.utils.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var homeHomeRecipeAdapter: HomeRecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(fragment_home_toolbar)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.home_refresh -> {
                homeViewModel.getFreshData()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun observeData() {
        homeViewModel.homeMediatorLiveData.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                when (resource) {
                    is Resource.Loading -> {
                        fragment_home_recycle_view.visibility = View.GONE
                        fragment_home_shimmer_layout.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        fragment_home_shimmer_layout.visibility = View.GONE
                        fragment_home_recycle_view.visibility = View.VISIBLE
                        homeHomeRecipeAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        fragment_home_shimmer_layout.visibility = View.GONE
                        fragment_home_recycle_view.visibility = View.GONE
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupView() {
        homeHomeRecipeAdapter = HomeRecipeAdapter()
        fragment_home_recycle_view.apply {
            adapter = homeHomeRecipeAdapter
            addItemDecoration(LinearItemDecoration(16, 16))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}