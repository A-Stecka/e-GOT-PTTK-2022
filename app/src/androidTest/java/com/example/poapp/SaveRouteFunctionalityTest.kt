package com.example.poapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.view.MainActivity
import com.example.poapp.view.tourist.route.MountainPassPickAdapter
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class SaveRouteFunctionalityTest {
    private val alert = "Uwaga"

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, true)


    @Test
    fun testShowLayout() {
        onView(withId(R.id.saveRouteAddProof)).check(matches(isDisplayed()))
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_proof_button)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.end_button)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
    }

    @Test
    fun testAddSectionVerifyTime() {
        val point1 = "Parking TPN"
        val point2 = "Rusinowa Polana"
        val pass1points = "6"
        val time1 = "-5"
        val time2 = "20"
        onView(withId(R.id.saveRouteAddProof)).check(matches(isDisplayed()))
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.own_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(5))
        onView(Matchers.allOf(withChild(withText(point1)), withChild(withText(point2)), withChild(withText(pass1points)))).perform(click())
        onView(withText(point1)).check(matches(isDisplayed()))
        onView(withId(R.id.time_value)).perform(replaceText(time1))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.time_value)).perform(replaceText(time2))
        onView(withId(R.id.add_route_pass_button)).perform(click())
    }

    @LargeTest
    @Test
    fun testAddSectionAndTime() {
        val point1 = "Parking TPN"
        val point2 = "Rusinowa Polana"
        val pass1points = "6"
        val time1 = "50"
        val point3 = "Wierch Poroniec"
        val pass2points = "4"
        val time2 = "30"
        val point4 = "Łysa Polana"
        val pass3points = "3"
        val time3 = "20"
        onView(withId(R.id.saveRouteAddProof)).check(matches(isDisplayed()))
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.own_button)).check(matches(isDisplayed()))
        onView(withId(R.id.own_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(5))
        onView(Matchers.allOf(withChild(withText(point1)), withChild(withText(point2)), withChild(withText(pass1points)))).perform(click())
        onView(withText(point1)).check(matches(isDisplayed()))
        onView(withId(R.id.time_value)).perform(replaceText(time1))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point1)), withChild(withText(point2)), withChild(withText(pass1points)))).check(matches(isDisplayed()))
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point2)), withChild(withText(pass2points)))).perform(click())
        onView(withText(point2)).check(matches(isDisplayed()))
        onView(withId(R.id.time_value)).perform(replaceText(time2))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point2)), withChild(withText(pass2points)))).check(matches(isDisplayed()))
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(8))
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point4)), withChild(withText(pass3points)))).perform(click())
        onView(withText(point4)).check(matches(isDisplayed()))
        onView(withId(R.id.time_value)).perform(replaceText(time3))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point4)), withChild(withText(pass3points)))).check(matches(isDisplayed()))
        onView(withId(R.id.end_button)).perform(click())
        onView(withId(R.id.choose_date_label)).check(matches(isDisplayed()))
        onView(withId(R.id.save_route_btn)).perform(click())
    }

    @Test
    fun testRoutePointsVerity() {
        val point1 = "Parking TPN"
        val point2 = "Rusinowa Polana"
        val pass1points = "6"
        val time1 = "50"
        val point3 = "Wierch Poroniec"
        val point4 = "Łysa Polana"
        val pass2points = "3"
        onView(withId(R.id.saveRouteAddProof)).check(matches(isDisplayed()))
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.own_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(5))
        onView(Matchers.allOf(withChild(withText(point1)), withChild(withText(point2)), withChild(withText(pass1points)))).perform(click())
        onView(withId(R.id.time_value)).perform(replaceText(time1))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(8))
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point4)), withChild(withText(pass2points)))).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
    }

}