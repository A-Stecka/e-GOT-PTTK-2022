package com.example.poapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.rule.ActivityTestRule
import com.example.poapp.view.MainActivity
import com.example.poapp.view.leader.RouteSectionSmallAdapter
import com.example.poapp.view.leader.RoutesToConfirmAdapter
import com.example.poapp.view.member.MountainPassAdapter
import com.example.poapp.view.tourist.route.RouteAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConfirmRouteFunctionalityTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun createRouteWithLeaderProof() {
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.add_route_button)).perform(click())
        onView(withId(R.id.add_mountain_pass_button)).perform(click())
        onView(withId(R.id.official_button)).perform(click())
        onView(withId(R.id.mountain_passes_list_pick)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.time_value)).perform(replaceText("55"))
        onView(withId(R.id.add_route_pass_button)).perform(click())
        onView(withId(R.id.add_proof_button)).perform(click())
        onView(withId(R.id.add_leader)).perform(click())
        onView(withId(R.id.leader_id)).perform(replaceText("1"))
        onView(withId(R.id.leader_proof_save)).perform(click())
        onView(withId(R.id.end_button)).perform(click())
        onView(withId(R.id.save_route_btn)).perform(click())
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<MountainPassAdapter.MountainPassOfficialItemHolder>(0, click()))
        onView(withId(R.id.send_to_leader)).perform(click())
        onView(withText("OK")).perform(click())
        Espresso.pressBack()
        Espresso.pressBack()
    }

    @Test
    fun testShowLayout() {
        val routesToConfirm = "Trasy do potwierdzenia:"
        val confirmRoute = "Potwierdź trasę"
        val routeSections = "Odcinki trasy"
        val proof = "Dowód:"
        val mountainPasses = "Odcinki:"
        val alert = "Uwaga"
        val cancel = "Cofnij"
        onView(withId(R.id.confirmRoute)).check(matches(isDisplayed()))
        onView(withId(R.id.confirmRoute)).perform(click())
        onView(withText(routesToConfirm)).check(matches(isDisplayed()))
        onView(withId(R.id.confirm_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RoutesToConfirmAdapter.RouteItemHolder>(0, click()))
        onView(withText(confirmRoute)).check(matches(isDisplayed()))
        onView(withId(R.id.show_route_sections)).check(matches(isDisplayed()))
        onView(withId(R.id.show_on_map)).check(matches(isDisplayed()))
        onView(withId(R.id.show_route_proofs)).check(matches(isDisplayed()))
        onView(withId(R.id.reject_route)).check(matches(isDisplayed()))
        onView(withId(R.id.confirm_route)).check(matches(isDisplayed()))
        onView(withId(R.id.show_route_sections)).perform(click())
        onView(withText(routeSections)).check(matches(isDisplayed()))
        onView(withId(R.id.close_route_sections)).check(matches(isDisplayed()))
        onView(withId(R.id.route_sections_small_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteSectionSmallAdapter.RouteSectionSmallItemHolder>(0, click()))
        onView(withText(proof)).check(matches(isDisplayed()))
        onView(withId(R.id.close_section_details)).check(matches(isDisplayed()))
        onView(withId(R.id.close_section_details)).perform(click())
        onView(withText(routeSections)).check(matches(isDisplayed()))
        onView(withId(R.id.close_route_sections)).perform(click())
        onView(withText(confirmRoute)).check(matches(isDisplayed()))
        onView(withId(R.id.show_on_map)).perform(click())
        onView(withId(R.id.map)).check(matches(isDisplayed()))
        Espresso.pressBack()
        onView(withText(confirmRoute)).check(matches(isDisplayed()))
        onView(withId(R.id.show_route_proofs)).perform(click())
        onView(withText(mountainPasses)).check(matches(isDisplayed()))
        onView(withText(proof)).check(matches(isDisplayed()))
        onView(withId(R.id.close)).check(matches(isDisplayed()))
        onView(withId(R.id.close)).perform(click())
        onView(withId(R.id.reject_route)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText(cancel)).perform(click())
        onView(withId(R.id.confirm_route)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText(cancel)).perform(click())
    }

    @Test
    fun testConfirmRoute() {
        val alert = "Uwaga"
        val ok = "OK"
        val routesToConfirm = "Trasy do potwierdzenia:"
        onView(withId(R.id.confirmRoute)).perform(click())
        onView(withId(R.id.confirm_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RoutesToConfirmAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.confirm_route)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText(ok)).perform(click())
        onView(withText(routesToConfirm)).check(matches(isDisplayed()))
        Espresso.pressBack()
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.edit_proofs)).check(matches(isNotEnabled()))
        onView(withId(R.id.edit_route)).check(matches(isNotEnabled()))
        onView(withId(R.id.delete_route)).check(matches(isNotEnabled()))
        onView(withId(R.id.send_to_leader)).check(matches(isNotEnabled()))
    }

    @Test
    fun testRejectRoute() {
        val alert = "Uwaga"
        val ok = "OK"
        val routesToConfirm = "Trasy do potwierdzenia:"
        onView(withId(R.id.confirmRoute)).perform(click())
        onView(withId(R.id.confirm_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RoutesToConfirmAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.reject_route)).perform(click())
        onView(withText(alert)).check(matches(isDisplayed()))
        onView(withText(ok)).perform(click())
        onView(withText(routesToConfirm)).check(matches(isDisplayed()))
        Espresso.pressBack()
        onView(withId(R.id.saveRouteAddProof)).perform(click())
        onView(withId(R.id.last_routes_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RouteAdapter.RouteItemHolder>(0, click()))
        onView(withId(R.id.edit_proofs)).check(matches(isNotEnabled()))
        onView(withId(R.id.edit_route)).check(matches(isNotEnabled()))
        onView(withId(R.id.delete_route)).check(matches(isNotEnabled()))
        onView(withId(R.id.send_to_leader)).check(matches(isNotEnabled()))
    }

}