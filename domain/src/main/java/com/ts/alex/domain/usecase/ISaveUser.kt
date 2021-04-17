package com.ts.alex.domain.usecase

import com.ts.alex.domain.model.User

interface ISaveUser {
    operator fun invoke(user: User)
}