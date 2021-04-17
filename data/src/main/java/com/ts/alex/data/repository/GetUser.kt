package com.ts.alex.data.repository

import com.google.gson.Gson
import com.ts.alex.data.datasource.IGetUserDataSource
import com.ts.alex.domain.model.User
import com.ts.alex.domain.usecase.IGetUser

class GetUser(private val iGetUserDataSource: IGetUserDataSource): IGetUser {
    override fun invoke(): User {
        val userString = iGetUserDataSource.getUser()
        return Gson().fromJson(userString, User::class.java)
    }
}