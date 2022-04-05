package com.grappim.weatherninetwothree.domain.interactor.auth

import com.grappim.weatherninetwothree.domain.storage.AuthenticationStorage
import javax.inject.Inject

class TokenInteractor @Inject constructor(
    private val authenticationStorage: AuthenticationStorage
) {

    fun saveToken(token: String) {
        authenticationStorage.token = token
    }

}