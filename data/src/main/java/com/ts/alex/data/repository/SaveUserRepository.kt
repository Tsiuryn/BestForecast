package com.ts.alex.data.repository

import com.google.gson.Gson
import com.ts.alex.data.datasource.ISaveUserDataSource
import com.ts.alex.domain.model.User
import com.ts.alex.domain.usecase.ISaveUserUseCase

class SaveUserRepository(private val saveUserDataSource: ISaveUserDataSource): ISaveUserUseCase {
    override fun invoke(user: User) {
        val user = Gson().toJson(user)
        saveUserDataSource.saveUser(user)
    }
}