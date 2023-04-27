package com.alex.scotiagit.ui.home

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.alex.scotiagit.MainActivity
import com.alex.scotiagit.R
import junit.framework.Assert.assertEquals
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeScreenFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.alex.scotiagit", appContext.packageName)
    }

    @Test
    fun testIfInitialStateLoadsSuccessfully() {
        onView(withText(R.string.main_screen_search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.search_input_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.photo_image_view)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.name_text_view)).check(matches(withText("")))
        onView(withId(R.id.repos_recycler)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testLoadingDataWithDelayScenarioSuccessfully() {
        val testUserId = "octocat"
        val expectedUserName = "The Octocat"

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.photo_image_view)).check(matches(isDisplayed()))
        onView(withText(expectedUserName)).check(matches(isDisplayed()))
        val storeNameVI: ViewInteraction = onView(withId(R.id.name_text_view))
        assertTextViewText(storeNameVI, expectedUserName)
    }

    @Test //this test has vulnerable
    fun testListOfRepoLoadsDisplaysUserRepos() {
        val testUserId = "octocat"

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.repos_recycler))
            .perform(RecyclerViewActions.scrollToPosition<ReposAdapter.RepoViewHolder>(2))
    }

    @Test
    fun testListOfRepoLoadsDisplaysUserReposFail() {
        val testUserId = "octocatkdkds"

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.status_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testIfUserCanOpenDetailsDialog() {
        val testUserId = "octocat"

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()
        onView(withId(R.id.repos_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ReposAdapter.RepoViewHolder>(
                    2,
                    click()
                )
            )

        // check if dialog displayed
        onView(withText(R.string.main_screen_close_button))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testWrongUserIdHasCorrectState() {
        val testUserId = "octocatfailuser"

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.status_view)).check(matches(isDisplayed()))
        onView(withId(R.id.name_text_view)).check(matches(withText("")))
        onView(withId(R.id.photo_image_view)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun testEmptyUserIdHasCorrectState() {
        val testUserId = ""

        insertSearchText(testUserId)
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.status_view)).check(matches(isDisplayed()))
        onView(withId(R.id.name_text_view)).check(matches(withText("")))
        onView(withId(R.id.photo_image_view)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun testNoUserIdHasCorrectState() {
        onView(withText(R.string.main_screen_search_button)).perform(click())
        waitForDataLoads()

        // check results
        onView(withId(R.id.status_view)).check(matches(isDisplayed()))
        onView(withId(R.id.name_text_view)).check(matches(withText("")))
        onView(withId(R.id.photo_image_view)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    //TODO: Common methods might be moved to base test or extensions or utils class.
    // Depends on the project code style guidelines.
    private fun insertSearchText(value: String) {
        onView(
            allOf(
                isDescendantOfA(withId(R.id.search_input_layout)),
                withClassName(endsWith("EditText"))
            )
        ).perform(
            typeText(value)
        )
    }

    // method will be used in the future
    private fun assertTextViewText(matcher: ViewInteraction, expectedValue: String) {
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return expectedValue
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                val text = tv.text.toString()
                assertEquals(text, expectedValue)
            }
        })
    }

    // well this is not good practice, for this approach we should we might use CountDownLatch or our own idle mechanism
    private fun waitForDataLoads() = Thread.sleep(7000L)
}