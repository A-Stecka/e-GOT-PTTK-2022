package com.example.poapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.view.MainActivity
import com.example.poapp.view.tourist.proof.ProofListAdapter
import com.example.poapp.view.tourist.proof.RouteSectionsPickAdapter
import com.example.poapp.view.tourist.route.MountainPassPickAdapter
import com.example.poapp.view.tourist.route.RouteAdapter
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class AddProofFunctionalityTest {
    private val alert = "Uwaga"

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, true)

    private fun addTestRouteSection() {
        val point1 = "Parking TPN"
        val point2 = "Rusinowa Polana"
        val pass1points = "6"
        val time1 = "50"
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.own_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(5))
        onView(Matchers.allOf(withChild(withText(point1)), withChild(withText(point2)), withChild(withText(pass1points)))).perform(click())
        onView(withText(point1)).check(matches(isDisplayed()))
        onView(withId(R.id.time_value)).perform(replaceText(time1))
        onView(withId(R.id.add_route_pass_button)).perform(click())
    }

    private fun addTestRouteShort() {
        onView(withId(R.id.add_route_button)).perform(click())
        addTestRouteSection()
        onView(withId(R.id.end_button)).perform(click())
        onView(withId(R.id.choose_date_label)).check(matches(isDisplayed()))
        onView(withId(R.id.save_route_btn)).perform(click())
    }

    @Test
    fun testShowLayoutNewRoute() {
        onView(withId(R.id.saveRouteAddProof)).check(matches(isDisplayed()))
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_proof_button)).check(matches(isDisplayed()))
        onView(withId(R.id.add_proof_button)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        addTestRouteSection()
        onView(withId(R.id.add_proof_button)).perform(click())
        onView(withId(R.id.add_image)).check(matches(isDisplayed()))
        onView(withId(R.id.add_leader)).check(matches(isDisplayed()))
        onView(withId(R.id.add_image)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.add_leader)).perform(click())
    }

    @Test
    fun testShowLayoutEditRoute() {
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        addTestRouteShort()
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.edit_proofs)).perform(click())
        onView(withId(R.id.add_proof)).perform(click())
        onView(withId(R.id.add_image)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.add_leader)).perform(click())
        Espresso.pressBack()
        Espresso.pressBack()
        onView(withId(R.id.see_proofs)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.delete_proof)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
    }


    private fun addTestRouteLong() {
        val point2 = "Rusinowa Polana"
        val point3 = "Wierch Poroniec"
        val pass2points = "4"
        val time2 = "30"
        val point4 = "Łysa Polana"
        val pass3points = "3"
        val time3 = "20"
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        addTestRouteSection()
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point2)), withChild(withText(pass2points)))).perform(click())
        onView(withId(R.id.time_value)).perform(replaceText(time2))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.scrollToPosition<MountainPassPickAdapter<MountainPassOfficial>.MountainPassItemHolder>(8))
        onView(Matchers.allOf(withChild(withText(point3)), withChild(withText(point4)), withChild(withText(pass3points)))).perform(click())
        onView(withId(R.id.time_value)).perform(replaceText(time3))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(withId(R.id.end_button)).perform(click())
        onView(withId(R.id.choose_date_label)).check(matches(isDisplayed()))
        onView(withId(R.id.save_route_btn)).perform(click())
    }

    @Test
    fun testAddLeaderProof() {
        val leaderId = "1"
        addTestRouteLong()
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.edit_proofs)).perform(click())
        onView(withId(R.id.add_proof)).perform(click())
        onView(withId(R.id.add_leader)).perform(click())
        onView(withId(R.id.leader_id)).perform(replaceText(leaderId))
        onView(withId(R.id.leader_proof_save)).perform(click())
        onView(withId(R.id.see_proofs)).perform(click())
        onView(withText("Przodownik:")).check(matches(isDisplayed()))
        onView(withId(R.id.close)).perform(click())
        onView(withId(R.id.delete_proof)).perform(click())
        onView(withId(R.id.proof_list)).perform(RecyclerViewActions.actionOnItemAtPosition<ProofListAdapter.ProofItemHolder>(0, click()))
        onView(withId(R.id.close)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
        onView(withId(R.id.see_proofs)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText("OK")).perform(click())
    }

    @Test
    fun testAddImageProof(){
        addTestRouteLong()
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.edit_proofs)).perform(click())
        onView(withId(R.id.add_proof)).perform(click())
        onView(withId(R.id.add_image)).perform(click())
        onView(withId(R.id.route_section_pick_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteSectionsPickAdapter.RouteSectionItemHolder>(0, click()))
        onView(withId(R.id.route_section_pick_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteSectionsPickAdapter.RouteSectionItemHolder>(1, click()))
        onView(withId(R.id.pick_picture_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testHasProof(){
        val point1 = "Parking TPN"
        val point2 = "Rusinowa Polana"
        val point3 = "Wierch Poroniec"
        val pass2points = "4"
        val time2 = "30"
        val leaderId = "1"
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        addTestRouteSection()
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point2)), withChild(withText(point3)), withChild(withText(pass2points)))).perform(click())
        onView(withId(R.id.time_value)).perform(replaceText(time2))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(Matchers.allOf(withChild(withText(point2)), withChild(withText(point3)), withChild(withText("NIE")))).check(matches(isDisplayed()))
        onView(Matchers.allOf(withChild(withText(point2)), withChild(withText(point1)), withChild(withText("NIE")))).check(matches(isDisplayed()))
        onView(withId(R.id.add_proof_button)).perform(click())
        onView(withId(R.id.add_leader)).perform(click())
        onView(withId(R.id.leader_id)).perform(replaceText(leaderId))
        onView(withId(R.id.leader_proof_save)).perform(click())
        onView(Matchers.allOf(withChild(withText(point2)), withChild(withText(point3)), withChild(withText("TAK")))).check(matches(isDisplayed()))
        onView(Matchers.allOf(withChild(withText(point2)), withChild(withText(point1)), withChild(withText("TAK")))).check(matches(isDisplayed()))
    }


}