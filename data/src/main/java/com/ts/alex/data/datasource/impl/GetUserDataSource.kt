package com.ts.alex.data.datasource.impl

import com.ts.alex.data.datasource.IGetUserDataSource
import com.ts.alex.data.local.prererences.SharedPreference

class GetUserDataSource(private val sharedPreference: SharedPreference): IGetUserDataSource {
    override fun getUser(): String {
        return sharedPreference.getUser()?: ""
    }


}