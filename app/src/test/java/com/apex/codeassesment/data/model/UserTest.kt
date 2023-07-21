package com.apex.codeassesment.data.model

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class UserTest {


    @Test
    fun  CreateUser() {
        TestCase.assertNotNull( User.createRandom())
    }

    @Test
    fun testEmptyOrNull() {
        val user = User.createRandom()
        assertNotNull(user.name?.first)
        assertNotEquals("", user.name?.first)
    }

    @Test
    fun validAge(){
        val user = User.createRandom()
        assertTrue(user.dob?.age ?: 0 > 0)
    }
    @Test
    fun  CheckValues() {
        TestCase.assertEquals(25, User.createRandom().dob?.age)
    }
}