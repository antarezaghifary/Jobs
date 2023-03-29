package com.reza.jobs.ui.screen.home

import com.reza.jobs.data.model.PositionModel

interface HomeJobNavigator {
    fun navigateToDetail(data: PositionModel.Response.Data)
}