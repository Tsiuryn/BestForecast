package com.ts.alex.bestforecast.di

import com.ts.alex.bestforecast.ui.forecast.main_screen.ForecastMainViewModel
import com.ts.alex.bestforecast.ui.forecast.settings.SettingsViewModel
import com.ts.alex.bestforecast.ui.registration.RegistrationViewModel
import com.ts.alex.bestforecast.ui.start.StartViewModel
import com.ts.alex.data.datasource.IGetForecastDataSource
import com.ts.alex.data.datasource.IGetUserDataSource
import com.ts.alex.data.datasource.ISaveUserDataSource
import com.ts.alex.data.datasource.IWorkWithCityDataSource
import com.ts.alex.data.datasource.impl.GetForecastDataSource
import com.ts.alex.data.datasource.impl.GetUserDataSource
import com.ts.alex.data.datasource.impl.SaveUserDataSource
import com.ts.alex.data.datasource.impl.WorkWithCityDataSource
import com.ts.alex.data.local.prererences.SharedPreference
import com.ts.alex.data.network.RestApi
import com.ts.alex.data.network.providePlaceHolderApi
import com.ts.alex.data.repository.GetForecastRepository
import com.ts.alex.data.repository.GetUserRepository
import com.ts.alex.data.repository.SaveUserRepository
import com.ts.alex.data.repository.WorkWithCityRepository
import com.ts.alex.domain.usecase.IGetForecastUseCase
import com.ts.alex.domain.usecase.IGetUserUseCase
import com.ts.alex.domain.usecase.ISaveUserUseCase
import com.ts.alex.domain.usecase.IWorkWithCity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val singleModule = module {
    viewModel { StartViewModel() }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel {
        ForecastMainViewModel(
            context = get(),
            getForecast = get(),
            prefUser = get(),
            prefCity = get()
        )
    }
    viewModel { SettingsViewModel(iGetUser = get()) }

    factory { SaveUserRepository(saveUserDataSource = get()) as ISaveUserUseCase }
    factory { GetUserRepository(iGetUserDataSource = get()) as IGetUserUseCase }
    factory { GetForecastRepository(iGetForecastDataSource = get()) as IGetForecastUseCase }
    factory { WorkWithCityRepository(workWithCityDataSource = get()) as IWorkWithCity }

    factory { SaveUserDataSource(sharedPreference = get()) as ISaveUserDataSource }
    factory { GetUserDataSource(sharedPreference = get()) as IGetUserDataSource }
    factory { GetForecastDataSource(restApi = get()) as IGetForecastDataSource }
    factory { WorkWithCityDataSource(preference = get()) as IWorkWithCityDataSource }

    single { SharedPreference(get()) }
    single { providePlaceHolderApi() as RestApi }
}
