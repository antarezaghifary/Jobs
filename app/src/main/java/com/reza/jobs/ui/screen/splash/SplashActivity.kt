package com.reza.jobs.ui.screen.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reza.jobs.R
import com.reza.jobs.databinding.ActivitySplashBinding
import com.reza.jobs.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(),
    SplashNavigator {

    private val splashViewModel: SplashViewModel by inject()
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        splashViewModel.navigator = this
    }
    override fun setLayout(): Int = R.layout.activity_splash

    override fun getViewModels(): SplashViewModel {
        TODO("Not yet implemented")
    }

    override fun navigateToHomeJob() {
        TODO("Not yet implemented")
    }
}