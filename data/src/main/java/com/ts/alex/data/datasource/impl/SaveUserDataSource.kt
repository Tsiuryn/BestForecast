package com.ts.alex.data.datasource.impl

import com.ts.alex.data.datasource.ISaveUserDataSource
import com.ts.alex.data.local.prererences.SharedPreference

class SaveUserDataSource (private val sharedPreference: SharedPreference):
    ISaveUserDataSource {
    override fun saveUser(user: String) {
        sharedPreference.saveUser(user)
    }
}