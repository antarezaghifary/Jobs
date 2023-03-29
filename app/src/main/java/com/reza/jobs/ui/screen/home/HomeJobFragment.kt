package com.reza.jobs.ui.screen.home

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.reza.jobs.R
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.databinding.FragmentHomeJobBinding
import com.reza.jobs.ui.base.BaseFragment
import com.reza.jobs.ui.screen.detail.DetailJobActivity
import com.reza.jobs.util.Status
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
        onGetPosition()
        initAdapter()
    }

    companion object {
        const val EXTRA_DATA_JOB = "EXTRA_DATA_JOB"
    }

    override fun setLayout(): Int = R.layout.fragment_home_job

    override fun getViewModels(): HomeJobViewModel = homeViewModel

    override fun onReadyAction() {
        handleListPosition()
    }

    private fun initAdapter() {
        binding?.rvPosition?.also {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            it.layoutManager = llm
        }
        itemPositionAdapter = ItemPositionAdapter {
            navigateToDetail(it)
        }
    }

    private fun onGetPosition() {
        homeViewModel.getListPosition()
    }

    private fun handleListPosition() {
        homeViewModel.listPosition.observe(this) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    it.data?.toMutableList().let {
                        itemPositionAdapter.submitList(it)
                        binding?.rvPosition?.adapter = itemPositionAdapter
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.throwable.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        }
    }

    override fun navigateToDetail(data: PositionModel.Response.Data) {
        val intent = Intent(requireContext(), DetailJobActivity::class.java)
        intent.putExtra(EXTRA_DATA_JOB, data)
        startActivity(intent)
    }
}