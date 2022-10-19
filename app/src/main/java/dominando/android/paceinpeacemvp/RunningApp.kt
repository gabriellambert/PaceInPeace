package dominando.android.paceinpeacemvp

import android.app.Application
import dominando.android.paceinpeacemvp.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class RunningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RunningApp)
            modules(
                androidModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}