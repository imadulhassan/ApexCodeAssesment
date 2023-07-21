package com.apex.codeassesment.ui.details

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.Coordinates
import com.apex.codeassesment.data.model.Dob
import com.apex.codeassesment.data.model.Location
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.Picture
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.location.LocationActivity
import com.google.android.material.button.MaterialButton
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
class DetailsActivityTest   {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(DetailsActivity::class.java)

    @Test
    fun testSetData() {
        // Launch the activity
        val activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Create a mock user object to pass to the setData() function
            val user = User(
                name = Name("John", "Doe"),
                gender = "Male",
                dob = Dob(age = 30),
                location = Location(
                    coordinates = Coordinates(latitude = "40.7128", longitude = "-74.0060")
                ),
                picture = Picture(large = "https://example.com/johndoe.jpg")
            )

            // Call the setData() function
            activity.setData(user)

            // Verify that the UI components have been set with the correct data
            val nameText = activity.findViewById<TextView>(R.id.details_name).text.toString()
            assertEquals("John Doe", nameText)

            val emailText = activity.findViewById<TextView>(R.id.details_email).text.toString()
            assertEquals("Male", emailText)

            val ageText = activity.findViewById<TextView>(R.id.details_age).text.toString()
            assertEquals("30", ageText)

            val locationText = activity.findViewById<TextView>(R.id.details_location).text.toString()
            assertEquals("Latitude: 40.7128, Longitude: -74.0060", locationText)
        }
    }


    @Test
    fun testNavigateLocation() {
        // Launch the activity
        val activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Create a mock user object to pass to the setData() function
            val coordinates = Coordinates(latitude = "40.7128", longitude = "-74.0060")
            val user = User(location = Location(coordinates = coordinates))

            // Set the user data
            activity.setData(user)

            // Click on the detailsLocationButton
            val locationButton = activity.findViewById<MaterialButton>(R.id.details_location_button)
            locationButton.performClick()

            // Verify that the LocationActivity is launched with the correct extras
            val expectedIntent = Intent(activity, LocationActivity::class.java)
            expectedIntent.putExtra("user-latitude-key", "40.7128")
            expectedIntent.putExtra("user-longitude-key", "-74.0060")
            val shadowActivity = Shadows.shadowOf(activity)
            val actualIntent = shadowActivity.nextStartedActivity
            assertEquals(expectedIntent.component, actualIntent.component)
            assertEquals(expectedIntent.extras, actualIntent.extras)
        }
    }
}