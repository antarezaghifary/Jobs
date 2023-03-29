package com.reza.jobs.ui.screen.detail

import android.os.Bundle
import com.reza.jobs.R
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.databinding.ActivityDetailJobBinding
import com.reza.jobs.ui.base.BaseActivity
import com.reza.jobs.util.SpannableStringHelper
import org.koin.android.ext.android.inject

class DetailJobActivity : BaseActivity<ActivityDetailJobBinding, DetailJobViewModel>(), DetailJobNavigator {

    private val detailJobViewModel: DetailJobViewModel by inject()
    private var _binding: ActivityDetailJobBinding? = null
    private val binding get() = _binding

    private lateinit var dataJob: PositionModel.Response.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        initLayout()
        initListener()
    }

    companion object {
        const val EXTRA_DATA_JOB = "EXTRA_DATA_JOB"
    }

    override fun setLayout(): Int = R.layout.activity_detail_job

    override fun getViewModels(): DetailJobViewModel = detailJobViewModel

    private fun initLayout(){
        dataJob = intent.getParcelableExtra<PositionModel.Response.Data>(EXTRA_DATA_JOB) as PositionModel.Response.Data
        binding?.apply {

            tvPosition.text = dataJob.title
            tvCompany.text = dataJob.company
            tvWeb.text = dataJob.company_url
            tvTitle.text = dataJob.title
            txtFulltime.text = dataJob.type
            tvFulltime.text = dataJob.location
            tvDesc.text = SpannableStringHelper.fixListBulletHtml(
                this@DetailJobActivity,
                dataJob.description
            ) //dataJob.desc
        }
    }

    private fun initListener(){
        binding?.apply {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }

}