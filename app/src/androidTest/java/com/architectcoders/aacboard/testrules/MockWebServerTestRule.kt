package com.architectcoders.aacboard.testrules

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerTestRule : TestWatcher() {

    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        server = MockWebServer()
        server.start(8080)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}