package com.grappim.weatherninetwothree.data.network.authenticators

import com.grappim.weatherninetwothree.domain.interactor.auth.GetTokenUseCase
import com.grappim.weatherninetwothree.domain.interactor.auth.TokenInteractor
import com.grappim.weatherninetwothree.domain.interactor.utils.Try
import com.grappim.weatherninetwothree.domain.interactor.utils.withoutParams
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OauthTokenAuthenticator @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val tokenInteractor: TokenInteractor
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val token = getTokenUseCase.invoke(withoutParams())
            when (token) {
                is Try.Success -> {
                    tokenInteractor.saveToken(token = token.data.token)
                }
            }

            return@runBlocking null
        }
    }
}