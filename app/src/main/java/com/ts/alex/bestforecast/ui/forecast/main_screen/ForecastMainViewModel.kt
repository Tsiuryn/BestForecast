package com.ts.alex.bestforecast.ui.forecast.main_screen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ts.alex.device.setUpLocationListener
import com.ts.alex.domain.model.User
import com.ts.alex.domain.model.forecast.Forecast
import com.ts.alex.domain.usecase.IGetForecastUseCase
import com.ts.alex.domain.usecase.IGetUserUseCase
import com.ts.alex.domain.usecase.IWorkWithCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ForecastMainViewModel(
    private val context: Context,
    private val getForecast: IGetForecastUseCase,
    private val prefUser: IGetUserUseCase,
    private val prefCity: IWorkWithCity
) : ViewModel() {

    var myUser = "Hi,"

    var city = ""
    private set


    private val _progress: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val progress: Flow<Boolean> = _progress

    private val _error: MutableSharedFlow<Throwable> = MutableSharedFlow<Throwable>()
    val error: Flow<Throwable> = _error

    private val _weatherByCity = MutableSharedFlow<Forecast>()
    val weatherByCity: Flow<Forecast>
        get() = _weatherByLocation

    private val _weatherByLocation = MutableSharedFlow<Forecast>()
    val weatherByLocation: Flow<Forecast>
        get() = _weatherByLocation

    private val _showException = MutableSharedFlow<String>()
    val showException: Flow<String>
        get() = _showException

    private val _user = MutableSharedFlow<String>()
    val user: Flow<String>
        get() = _user

    fun getWeatherByCity(city: String) {
        viewModelScope.launch {
            handleLoading {
                _weatherByCity.emit(getForecast.getWeatherByCity(city))
            }
        }
    }

    fun getUser(){
        viewModelScope.launch {
            Log.d("TAG11", "getUser: ${prefUser.invoke()}")
            val user = prefUser.invoke()
            myUser = "Hi, ${user.name}"
            _user.emit(user.name)
        }
    }

    @SuppressLint("MissingPermission")
    fun getWeatherByCurrentLocation() {
        getLocation {
            viewModelScope.launch(Dispatchers.IO) {

                handleLoading {
                    val forecast = getForecast.getWeatherByLocation(
                        lat = it.latitude,
                        lon = it.longitude
                    )
                    _weatherByLocation.emit(forecast)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(listener: (Location) -> Unit) {
        viewModelScope.launch {
            _progress.emit(true)
                setUpLocationListener(context) {
                    listener(it)
                }
        }

    }

    private suspend inline fun handleLoading(
        block: () -> Unit
    ) {
        _progress.emit(true)
        try {
            block()
        } catch (ex: Throwable) {
            _error.emit(ex)
        } finally {
            _progress.emit(false)
        }
    }
}