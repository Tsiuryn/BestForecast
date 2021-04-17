package com.ts.alex.data.repository

import com.ts.alex.data.datasource.impl.WorkWithCityDataSource
import com.ts.alex.domain.usecase.IWorkWithCity

class WorkWithCityRepository(private val workWithCityDataSource: WorkWithCityDataSource)
    : IWorkWithCity{
    override fun getCity(): String {
        return workWithCityDataSource.getCity()
    }

    override fun setCity(city: String) {
        workWithCityDataSource.saveCity(city)
    }
}