package com.reza.jobs.ui.screen.home

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reza.jobs.R
import com.reza.jobs.databinding.FragmentHomeJobBinding
import com.reza.jobs.databinding.FragmentProfileBinding
import com.reza.jobs.ui.base.BaseActivity
import com.reza.jobs.ui.base.BaseFragment
import com.reza.jobs.ui.screen.profile.ProfileViewModel
import com.reza.jobs.ui.screen.splash.SplashNavigator
import org.koin.android.ext.android.inject

class HomeJobFragment : BaseFragment<FragmentHomeJobBinding, HomeJobViewModel>() {

    private val homeViewModel: HomeJobViewModel by inject()
    private var _binding: FragmentHomeJobBinding? = null
    private val binding get() = _binding

    override fun onInitialization() {
        super.onInitialization()
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
    }

    override fun setLayout(): Int = R.layout.fragment_home_job

    override fun getViewModels(): HomeJobViewModel = homeViewModel

    override fun onReadyAction() {
    }
}