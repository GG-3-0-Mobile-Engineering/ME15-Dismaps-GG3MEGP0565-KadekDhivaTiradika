package com.bydhiva.dismaps.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.testutils.TextInputLayoutActions
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun validateBaseFragmentContainer() {
        onView(withId(R.id.bottom_sheet)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.main_container)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.header_container)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.bs_header_container)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun mainFlow() {
        //set date Filter
        onView(withId(R.id.chip_filter)).perform(click())
        onView(withId(com.google.android.material.R.id.mtrl_picker_header_toggle)).perform(click())
        onView(withId(com.google.android.material.R.id.mtrl_picker_text_input_range_start)).perform(
            TextInputLayoutActions.replaceText("8/1/2023")
        )
        onView(withId(com.google.android.material.R.id.mtrl_picker_text_input_range_end)).perform(
            TextInputLayoutActions.replaceText("8/2/2023")
        )
        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click())

        //set disaster filter
        onView(withId(R.id.chip_disaster_flood)).perform(click())

        //check if recycler view displayed
        onView(withId(R.id.rv_disaster)).check(ViewAssertions.matches(isDisplayed()))
    }
}