package com.reza.jobs.ui.screen.home

import android.util.Log
import android.widget.Toast
import com.reza.jobs.R
import com.reza.jobs.databinding.FragmentHomeJobBinding
import com.reza.jobs.ui.base.BaseFragment
import com.reza.jobs.util.Status
import org.koin.android.ext.android.inject

class HomeJobFragment : BaseFragment<FragmentHomeJobBinding, HomeJobViewModel>() {

    private val homeViewModel: HomeJobViewModel by inject()
    private var _binding: FragmentHomeJobBinding? = null
    private val binding get() = _binding

    override fun onInitialization() {
        super.onInitialization()
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        onGetPosition()
    }

    override fun setLayout(): Int = R.layout.fragment_home_job

    override fun getViewModels(): HomeJobViewModel = homeViewModel

    override fun onReadyAction() {
        handleListPosition()
    }

    private fun onGetPosition() {
        homeViewModel.getListPosition()
    }

    private fun handleListPosition() {
        homeViewModel.listPosition.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "${it.data}", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.throwable.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        }
    }
}