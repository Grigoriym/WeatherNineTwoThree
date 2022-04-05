package com.grappim.weatherninetwothree.domain.interactor.auth

import com.grappim.weatherninetwothree.di.IoDispatcher
import com.grappim.weatherninetwothree.domain.interactor.utils.CoroutineUseCase
import com.grappim.weatherninetwothree.domain.interactor.utils.NoParams
import com.grappim.weatherninetwothree.domain.model.auth.Token
import com.grappim.weatherninetwothree.domain.repository.OauthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val oauthRepository: OauthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CoroutineUseCase<NoParams, Token>(ioDispatcher) {

    override suspend fun execute(parameters: NoParams): Token {
        return oauthRepository.getToken()
    }

}