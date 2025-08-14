package com.example.selling.ui

import android.app.Application
import com.example.selling.ui.di.myAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartup {
    override fun onKoinStartup()= KoinConfiguration {
        androidContext(this@MainApplication)
        modules(myAppModules)
    }
    override fun onCreate() {
        super.onCreate()

        }
    }