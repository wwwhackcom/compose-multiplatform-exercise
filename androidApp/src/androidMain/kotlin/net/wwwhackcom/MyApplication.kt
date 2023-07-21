package net.wwwhackcom

import android.app.Application
import net.wwwhackcom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            AndroidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}