package com.architectcoders.aacboard.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.testrules.MockWebServerTestRule
import com.architectcoders.aacboard.ui.activities.MainNavHostActivity
import com.architectcoders.aacboard.ui.fragments.SearchPictogramsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainInstrumentationTest {

    @get:Rule
    val mockWebServerRule = MockWebServerTestRule()

    val okHttpClient: OkHttpClient by KoinJavaComponent.inject(OkHttpClient::class.java)


    @get:Rule(order = 1)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(MainNavHostActivity::class.java)


    @Before
    fun setUp() {
        val json =
            InstrumentationRegistry.getInstrumentation().context.resources.assets.open("arasaac_response.json")
                .bufferedReader().use { it.readText() }
        val expectedResponse = MockResponse().setBody(json)
        mockWebServerRule.server.enqueue(expectedResponse)
        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun when_search_selected_received_pictogram_shown() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInContainer<SearchPictogramsFragment>(themeResId = R.style.Theme_AACBoard) {
            SearchPictogramsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.query_text)).perform(typeText("hola"), closeSoftKeyboard())
        onView(withId(R.id.search_button)).perform(click())

        onView(withId(R.id.pictogram_list))
            .check(matches(hasDescendant(withContentDescription("detached house"))))

        onView(withId(R.id.pictogram_list))
            .check(matches(hasDescendant(withContentDescription("detached house"))))

        //onView(withContentDescription("detached house")).perform((click()))

    }


    @Test
    fun when_new_board_created_a_picto_from_server_is_associated_to_it() {
        onView(withId(R.id.action_list_dashboards)).perform(click())

        onView(withId(R.id.new_dashboard_button)).perform(click())

        onView(withId(R.id.iv_new_dashboard_image)).perform(click())

        onView(withId(R.id.query_text)).perform(typeText("hola"), closeSoftKeyboard())
        onView(withId(R.id.search_button)).perform(click())

        onView(withId(R.id.pictogram_list))
            .check(matches(hasDescendant(withContentDescription("detached house"))))

        onView(withContentDescription("detached house")).perform((click()))

        onView(withId(R.id.iv_new_dashboard_image))
            .check(matches(withTagValue(`is`("https://static.arasaac.org/pictograms/5957/5957_300.png"))))
    }

}