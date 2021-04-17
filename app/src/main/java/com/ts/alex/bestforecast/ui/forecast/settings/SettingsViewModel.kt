package com.ts.alex.bestforecast.ui.forecast.settings

import androidx.lifecycle.ViewModel
import com.ts.alex.domain.usecase.IGetUserUseCase


class SettingsViewModel(
    private val iGetUser: IGetUserUseCase
) : ViewModel() {

    var name = "Name: not registered"
        private set
    var email = "Email: "
        private set
    var password = "Password: "
        private set


    init {
        val user = iGetUser()
        name = "Name: ${user.name}"
        email = "Email: ${user.email}"
        password = "Password: ${user.password}"
    }
}