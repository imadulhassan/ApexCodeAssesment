package com.apex.codeassesment.ui.main

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var binding: ActivityMainBinding

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()

        binding = spy(activity.binding!!) // Spy on the real binding
    }

    @Test
    fun `test RecyclerView Initialization`() {
        assertNotNull(binding.userListView.layoutManager)

    }


}