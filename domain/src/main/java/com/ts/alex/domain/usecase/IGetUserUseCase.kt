package com.ts.alex.domain.usecase

import com.ts.alex.domain.model.User

interface IGetUserUseCase {
    operator fun invoke(): User
}