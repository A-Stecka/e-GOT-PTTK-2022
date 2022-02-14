package com.example.poapp

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.poapp.view.MainActivity
import com.example.poapp.view.member.MountainPassAdapter
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

class EditMountainPassFunctionalityTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Test
    fun testShowLayout() {
        onView(withId(R.id.mountainPass)).check(matches(isDisplayed()))
        onView(withId(R.id.mountainPass)).perform(click())
        onView(withId(R.id.mountain_passes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withText("Szczegóły")).check(matches(isDisplayed()))
    }

    @Test
    fun testEditPoint() {
        val changedName = "Zmieniona nazwa"
        onView(withId(R.id.mountainPass)).check(matches(isDisplayed()))
        onView(withId(R.id.mountainPass)).perform(click())
        onView(withId(R.id.mountain_passes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.edit_button)).perform(click())
        onView(withId(R.id.edit_start)).perform(click())
        onView(withId(R.id.official_point_name)).perform(replaceText(changedName))
        onView(withId(R.id.save_official_point)).perform(click())
        onView(withId(R.id.new_start)).check(matches(withText(changedName)))
        onView(withId(R.id.save_mountain_pass)).perform(click())
        onView(withText(changedName)).check(matches(isDisplayed()))
    }

    @Test
    fun testPointMountainRangeValidation() {
        val changeRange = "Nowe Pasmo Test"
        val alert = "Uwaga"
        onView(withId(R.id.mountainPass)).check(matches(isDisplayed()))
        onView(withId(R.id.mountainPass)).perform(click())
        onView(withId(R.id.mountain_passes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.edit_button)).perform(click())
        onView(withId(R.id.edit_through)).perform(click())
        onView(withId(R.id.mountain_range)).perform(replaceText(changeRange))
        onView(withId(R.id.save_official_point)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
    }

    @Test
    fun testEditPassPoints() {
        val incorrectPoints = "-5"
        val correctPoints = "10"
        val alert = "Uwaga"
        onView(withId(R.id.mountainPass)).check(matches(isDisplayed()))
        onView(withId(R.id.mountainPass)).perform(click())
        onView(withId(R.id.mountain_passes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.edit_button)).perform(click())
        onView(withId(R.id.edit_points)).perform(click())
        onView(withId(R.id.edit_mountain_pass_points)).perform(replaceText(incorrectPoints))
        onView(withId(R.id.save_mountain_pass_points)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.edit_mountain_pass_points)).perform(replaceText(correctPoints))
        onView(withId(R.id.save_mountain_pass_points)).perform(click())
        onView(withId(R.id.save_mountain_pass)).perform(click())
        onView(withText(correctPoints)).check(matches(isDisplayed()))
    }

    @Test
    fun testChangeStatus() {
        val alert = "Uwaga"
        val active = "aktywny"
        val inactive = "zlikwidowany"
        onView(withId(R.id.mountainPass)).check(matches(isDisplayed()))
        onView(withId(R.id.mountainPass)).perform(click())
        onView(withId(R.id.mountain_passes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.edit_button)).perform(click())
        val status = getText(withId(R.id.new_status))
        val result = if (status == active) {
            inactive
        } else {
            active
        }
        onView(withId(R.id.edit_status)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withText(result)).check(matches(isDisplayed()))
        onView(withId(R.id.save_mountain_pass)).perform(click())
        onView(withText(result)).check(matches(isDisplayed()))
        Espresso.pressBack()
        onView(withId(R.id.mountain_passes_list))
            .check(matches(hasDescendant(withText(result))))
    }

    private fun getText(matcher: Matcher<View?>?): String? {
        val stringHolder = arrayOf<String?>(null)
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController?, view: View) {
                val tv = view as TextView
                stringHolder[0] = tv.text.toString()
            }
        })
        return stringHolder[0]
    }

}