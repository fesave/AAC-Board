package com.architectcoders.aacboard.testrules

import androidx.test.core.app.ApplicationProvider
import com.architectcoders.aacboard.di.localModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
class KoinRoomTestRule : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            loadKoinModules(localModule)
        }
    }

    override fun finished(description: Description) {
        super.finished(description)
        stopKoin()
    }
}