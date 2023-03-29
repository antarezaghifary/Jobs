package com.reza.jobs.ui.screen.home

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.reza.jobs.R
import com.reza.jobs.adapter.LoadMoreAdapter
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.databinding.FragmentHomeJobBinding
import com.reza.jobs.ui.base.BaseFragment
import com.reza.jobs.ui.screen.detail.DetailJobActivity
import com.reza.jobs.util.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout
import org.koin.android.ext.android.inject

class HomeJobFragment : BaseFragment<FragmentHomeJobBinding, HomeJobViewModel>(), HomeJobNavigator {

    private val homeViewModel: HomeJobViewModel by inject()
    private var _binding: FragmentHomeJobBinding? = null
    private val binding get() = _binding

    private lateinit var itemPositionAdapter: ItemPositionAdapter
    override fun onInitialization() {
        super.onInitialization()
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        initUI()
        initListener()
        initAdapter()
    }

    private fun initListener() {
        binding?.apply {
            btnSearch.setOnClickListener {
                homeViewModel.getListSearchPosition(
                    description = svPosition.query.toString(),
                    location = etLocation.text.toString()
                )
            }
        }
    }

    private fun initUI() {
        binding?.apply {
            ivFilter.setOnClickListener {
            (view?.findViewById<View>(
                    R.id.elSearch
                ) as ExpandableLayout).toggle()
            }
        }
    }

    companion object {
        const val EXTRA_DATA_JOB = "EXTRA_DATA_JOB"
    }

    override fun setLayout(): Int = R.layout.fragment_home_job

    override fun getViewModels(): HomeJobViewModel = homeViewModel

    override fun onReadyAction() {
        handlingPagingData()
    }

    private fun initAdapter() {
        itemPositionAdapter = ItemPositionAdapter {
            navigateToDetail(it)
        }
    }

    private fun handlingPagingData(){
        binding?.apply {
            lifecycleScope.launch {
                homeViewModel.jobList.collect{
                    itemPositionAdapter.submitData(it)
                }
            }
            rvPosition.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = itemPositionAdapter
            }
            lifecycleScope.launchWhenCreated {
                itemPositionAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    progressBottom.isVisible = state is LoadState.Loading
                }
            }
            rvPosition.adapter = itemPositionAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    itemPositionAdapter.retry()
                }
            )
        }
    }

    override fun navigateToDetail(data: PositionModel.Response.Data) {
        val intent = Intent(requireContext(), DetailJobActivity::class.java)
        intent.putExtra(EXTRA_DATA_JOB, data)
        startActivity(intent)
    }
}