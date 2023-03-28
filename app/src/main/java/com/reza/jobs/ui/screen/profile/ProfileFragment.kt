package com.reza.jobs.ui.screen.profile

import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.reza.jobs.R
import com.reza.jobs.databinding.FragmentProfileBinding
import com.reza.jobs.ui.base.BaseFragment
import com.reza.jobs.ui.screen.login.LoginActivity
import org.koin.android.ext.android.inject


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {


    private val profileViewModel: ProfileViewModel by inject()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onInitialization() {
        super.onInitialization()
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        initUI()
        initListener()
    }

    private fun initUI() {
        binding?.apply {
            gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                    .build()
            gsc = GoogleSignIn.getClient(requireActivity(), gso)

            val acct: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())!!
            if (acct != null) {
                val personName: String? = acct.displayName
                val personEmail: String? = acct.email
                name.text = personName
                email.text = personEmail
                if (acct.photoUrl != null) {
                    Glide.with(requireActivity())
                        .load(acct.photoUrl)
                        .into(imgProfile)
                } else {
                    imgProfile.setImageResource(R.drawable.ic_account)
                }
            }
        }
    }

    private fun initListener() {
        binding?.signout?.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener {
            activity?.finish()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    override fun onReadyAction() {}

    override fun setLayout(): Int = R.layout.fragment_profile

    override fun getViewModels(): ProfileViewModel = profileViewModel
}