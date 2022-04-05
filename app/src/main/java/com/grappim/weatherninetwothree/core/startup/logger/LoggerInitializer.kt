package com.grappim.weatherninetwothree.core.startup.logger

import android.content.Context
import androidx.startup.Initializer

class LoggerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        AndroidLoggingHandler.setup()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()
}