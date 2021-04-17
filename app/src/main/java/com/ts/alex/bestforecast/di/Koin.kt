package com.ts.alex.bestforecast.di

import com.ts.alex.bestforecast.ui.forecast.main_screen.ForecastMainViewModel
import com.ts.alex.bestforecast.ui.registration.RegistrationViewModel
import com.ts.alex.bestforecast.ui.start.StartViewModel
import com.ts.alex.data.datasource.IGetUserDataSource
import com.ts.alex.data.datasource.ISaveUserDataSource
import com.ts.alex.data.datasource.impl.GetUserDataSource
import com.ts.alex.data.datasource.impl.SaveUserDataSource
import com.ts.alex.data.local.prererences.SharedPreference
import com.ts.alex.data.repository.GetUser
import com.ts.alex.data.repository.SaveUser
import com.ts.alex.domain.usecase.IGetUser
import com.ts.alex.domain.usecase.ISaveUser
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val singleModule = module {
    viewModel { StartViewModel() }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { ForecastMainViewModel() }

    factory { SaveUser(saveUserDataSource = get()) as ISaveUser }
    factory { GetUser(iGetUserDataSource = get()) as IGetUser }
    factory { SaveUserDataSource(sharedPreference = get()) as ISaveUserDataSource }
    factory { GetUserDataSource(sharedPreference = get()) as IGetUserDataSource }
    single { SharedPreference(get()) }
}
