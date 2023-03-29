package com.reza.jobs.ui.screen.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.data.model.Resource
import com.reza.jobs.data.source.repository.JobRepository
import com.reza.jobs.ui.base.BaseViewModel
import com.reza.jobs.util.ErrorUtil
import kotlinx.coroutines.launch

class HomeJobViewModel (private val repository: JobRepository, application: Application) :
    BaseViewModel<HomeJobNavigator>(application) {

    private val _listPosition = MutableLiveData<Resource<List<PositionModel.Response.Data>>>()
    val listPosition : MutableLiveData<Resource<List<PositionModel.Response.Data>>>
    get() = _listPosition

    fun getListPosition() {
        viewModelScope.launch {
            _listPosition.postValue(Resource.loading())
            try {
                val response = repository.getPosition()
                _listPosition.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _listPosition.postValue(
                    Resource.error(
                        ErrorUtil.getErrorThrowableMsg(t),
                        null,
                        t
                    )
                )
            }
        }
    }
}