package com.reza.jobs.ui.screen.menu

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.reza.jobs.R
import com.reza.jobs.databinding.ActivityMenuBinding
import com.reza.jobs.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class MenuActivity : BaseActivity<ActivityMenuBinding, MenuViewModel>() {

    private val menuViewModel: MenuViewModel by inject()
    private var _binding: ActivityMenuBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        val navController = findNavController(R.id.nav_host_fragment)
        binding?.navView?.setupWithNavController(navController)
    }

    override fun setLayout(): Int = R.layout.activity_menu

    override fun getViewModels(): MenuViewModel = menuViewModel
}