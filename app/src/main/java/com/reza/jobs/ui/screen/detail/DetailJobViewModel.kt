package com.reza.jobs.ui.screen.detail

import android.app.Application
import com.reza.jobs.data.source.repository.JobRepository
import com.reza.jobs.ui.base.BaseViewModel

class DetailJobViewModel(private val repository: JobRepository, application: Application) :
    BaseViewModel<DetailJobNavigator>(application) {
}