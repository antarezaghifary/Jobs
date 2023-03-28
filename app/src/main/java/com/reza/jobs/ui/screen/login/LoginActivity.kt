package com.reza.jobs.ui.screen.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.appevents.UserDataStore.EMAIL
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.reza.jobs.R
import com.reza.jobs.databinding.ActivityLoginBinding
import com.reza.jobs.ui.base.BaseActivity
import com.reza.jobs.ui.screen.menu.MenuActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    LoginNavigator {

    private val loginViewModel: LoginViewModel by inject()
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding

    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null

    private lateinit var callbackManager: CallbackManager
    private var auth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        loginViewModel.navigator = this
        initListener()
    }

    override fun onStart() {
        super.onStart()
        fillDataGoogle()
        fillDataFacebook()
    }

    private fun initListener(){
        binding?.apply {
            btnGoogle.setOnClickListener {
                signInWithGoogle()
            }
            btnFacebook.setOnClickListener {
                signInWithFacebook()
            }
        }
    }

    private fun fillDataFacebook() {
        auth = FirebaseAuth.getInstance()
        currentUser = auth?.currentUser
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(application);
        callbackManager = CallbackManager.Factory.create()
        if(currentUser != null){
            reload()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun signInWithFacebook() {
        binding?.apply {
            btnFacebook.setReadPermissions(listOf(EMAIL))
            btnFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    handleFacebookAccessToken(loginResult!!.accessToken)
                }

                override fun onCancel() {
                    // App code
                    Log.e("TAG", "onCancel: ")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.e("TAG", "onError: ")
                }
            })
        }
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken){
        Log.e("TAG", "handleFacebookAccessToken: $accessToken")
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth?.currentUser
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    reload()
                }
            }
    }

    private fun reload() {
        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun fillDataGoogle(){
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)
    }

    private fun signInWithGoogle() {
        val signInIntent = gsc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                launch{
                    loginViewModel.successLogin().await()
                }
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if(requestCode == 2000){
            callbackManager.onActivityResult(requestCode, resultCode, data)
            launch{
                loginViewModel.successLogin().await()
            }
        }
    }

    override fun setLayout(): Int = R.layout.activity_login

    override fun getViewModels(): LoginViewModel = loginViewModel

    override fun navigateToHomeJob() {
        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}