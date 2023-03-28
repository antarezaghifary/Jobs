package com.reza.jobs.ui.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.reza.jobs.R
import com.reza.jobs.databinding.ActivitySplashBinding
import com.reza.jobs.ui.base.BaseActivity
import com.reza.jobs.ui.screen.login.LoginActivity
import com.reza.jobs.ui.screen.menu.MenuActivity
import kotlinx.coroutines.launch
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

    override fun getViewModels(): SplashViewModel = splashViewModel

    override fun onResume() {
        super.onResume()
        launch {
            splashViewModel.displaySplashAsync().await()
        }
    }

    override fun navigateToLoginJob() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            launch{
                splashViewModel.successLogin().await()
            }
        }else{
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun navigateToHomeJob() {
        val intent = Intent(this@SplashActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}