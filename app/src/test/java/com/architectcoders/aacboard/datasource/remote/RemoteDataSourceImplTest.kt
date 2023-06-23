package com.architectcoders.aacboard.datasource.remote

import com.architectcoders.aacboard.data.datasource.remote.RemoteDataSource
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.domain.data.Error.NoMatchFound
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.cell.CellPictogram
import com.architectcoders.aacboard.network.ArasaacNetworkInstance
import com.architectcoders.aacboard.network.service.ArasaacService
import com.architectcoders.aacboard.testrules.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var arasaacService: ArasaacService
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var server: MockWebServer

    @Before
    fun setup() {
        arasaacService =
            ArasaacNetworkInstance.createArasaacNetworkInstance("http://localhost:8080")
        remoteDataSource = RemoteDataSourceImpl(arasaacService)
        server = MockWebServer()
        server.start(8080)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }


    @Test
    fun `no pictogram found, http error 404, NoMathFound error returned`(): Unit = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        server.enqueue(expectedResponse)

        val response = remoteDataSource.searchPictos("es", "dfagfwteejbbjadgdjg")

        assertTrue(response is Response.Failure)
        assertTrue((response as Response.Failure).error is NoMatchFound)
    }

    @Test
    fun `unsupported language code, http error 400, Server error returned`(): Unit = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        server.enqueue(expectedResponse)

        val response = remoteDataSource.searchPictos("GJM", "casa")

        assertTrue(response is Response.Failure)
        assertTrue((response as Response.Failure).error is Error.Server)
        val errorCode = (response.error as Error.Server).code
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, errorCode)
    }

    @Test
    fun `successful search`(): Unit = runTest {
        val remoteDataSource = RemoteDataSourceImpl(arasaacService)
        // https://stackoverflow.com/a/61538784
        val json =
            File("src\\test\\assets\\arasaac_response.json").bufferedReader().use { it.readText() }
        val expectedResponse = MockResponse().setBody(json)
        server.enqueue(expectedResponse)

        val response = remoteDataSource.searchPictos("en", "house")

        assertTrue(response is Response.Success<List<CellPictogram>>)
        val result = (response as Response.Success<List<CellPictogram>>).result
        assertEquals(result.get(1).keyword, "house")
        assertTrue(result.get(1).url.contains("2317"))
        assertEquals(result.size, 19)
    }

}