package com.grappim.weatherninetwothree.data.storage

import android.content.Context
import com.grappim.weatherninetwothree.domain.storage.AuthenticationStorage
import com.grappim.weatherninetwothree.domain.storage.utils.string
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthenticationStorage {

    companion object{
        private const val STORAGE_NAME = "authentication_storage"

        private const val OAUTH_TOKEN = "oauth_token_key"
    }

    private val sharedPreferences = context
        .getSharedPreferences(
            STORAGE_NAME,
            Context.MODE_PRIVATE
        )

    override var token: String by sharedPreferences.string(OAUTH_TOKEN)

}